import { Selector } from 'testcafe';

fixture('Dummy Test').page('https://duckduckgo.com');

test('Wikipedia is in the front page', async ctrl => {
  await ctrl.expect('Dummy').eql('Not Dummy');
  await ctrl.expect(Selector('#links > .result:first-child .result__a', {timeout: 500}).getAttribute('href'))
               .eql('https://www.wikipedia.org/');
  await ctrl.expect('Dummy').eql('Dummy');
});

test('Find wikipedia', async ctrl => {
  await ctrl.typeText('#search_form_input_homepage', 'wikipedia')
            .click('#search_button_homepage')
            .expect(Selector('#links > .result:first-child .result__a').getAttribute('href')).eql('https://www.wikipedia.org/');
});
