When a [[User]] access any part of the OESS but it's not authenticated, the **Login Screen** appears instead. The screen consists on:

- A text input control, labelled "Email"
- A password input control, labelled "Password"
- A button labelled "Login"

The user enters their email and password in the screen, and then clicks the "Login" button, or presses the Enter key of their keyboard.

If the email and password don't match an user, the OESS returns to the Login Screen with the message *"The user email and/or password is incorrect"* visible to the user.

If the email and password does match an user, but such user is marked as *inactive*, the OESS returns to the Login Screen with the message *"The user is not available"* visible to the user.

If the email and password match an non-inactive user, it shows the [[Main Screen]] for the user.
