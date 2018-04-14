const TEST_RUN_PARAMS_FILE = './.testRunParams.json';
const fs = require('fs');
const createTestcafe = require('testcafe');
const testRunParamsFile = fs.existsSync(TEST_RUN_PARAMS_FILE) ? require('../' + TEST_RUN_PARAMS_FILE) : {};
const testRunParams = {
  browsers: testRunParamsFile.browser ? [testRunParamsFile.browser] : ['firefox', 'chrome', 'safari', 'edge', 'ie']
};

let testcafe = null;

function writeJSON (path, contents) {
  return new Promise((accept, reject) => {
    fs.writeFile(path, JSON.stringify(contents, null, 2), 'utf8', err => err ? reject(err) : accept());
  });
}

createTestcafe('localhost').then(testcafeInstance => {
  testcafe = testcafeInstance;

  function runWithPossible(browserList, index) {
    let browser = browserList[index];
    if (!browser) {
      if(!testRunParamsFile.browser) {
        testRunParamsFile.browser = null;
        return writeJSON(TEST_RUN_PARAMS_FILE, testRunParamsFile)
          .then(() => {
            console.error('The test were tried to be run in several browsers, but none of them were found');
            console.error('A file \'.testRunParams.json\' has been created with a \'browser\' attribute');
            console.error('Change the value of \'browser\' for a parameter that can be accepted as TestCafe browser');
            console.error('http://devexpress.github.io/testcafe/documentation/using-testcafe/programming-interface/runner.html#browsers');
            console.error('(browsers used: %s)', browserList);
          })
      } else {
        console.error('The browser \'%s\' is not available', testRunParamsFile.browser);
      }
    } else {
      let runner = testcafe.createRunner();
      return runner
        .src(fs.readdirSync('ui-test').filter(name => name.match(/test-.*\.js$/)).map(name => 'ui-test/' + name))
        .browsers(browser)
        .run()
        .then(failedCount => {
          if(!testRunParamsFile.browser) {
            testRunParamsFile.browser = browser;
            return writeJSON(TEST_RUN_PARAMS_FILE, testRunParamsFile);
          }
          return failedCount;
        }).catch(error => {
          if (error.message.startsWith('Unable to find the browser.')) {
            return runWithPossible(browserList, index + 1);
          } else {
            throw error;
          }
        });
    }
  }

  return runWithPossible(testRunParams.browsers, 0);
}).catch(err => {
  console.error('An unexpected error has been found: %s', err.message);
  console.error(err.stack);
}).finally(() => {
  if(testcafe !== null) testcafe.close().then(() => console.log('TestCafe server closed'));
});
