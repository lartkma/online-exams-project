**Teachers** are in charge of creating, scheduling and evaluating (when needed) exams for the [[students|Student]]. Students from a teacher's course are managed together using [[Groups|Group]].

## Adding new teachers
Teachers can only be invited by other teachers. To add another teacher, one of them should provide the new teacher's email. The system will send an email to them with a temporary link (up to 48 hours). In there, the new teacher inserts their data to create the user.

Teachers can also be invited using an special HTTP request. For security reasons, the request can only be made from the same place the server is deployed (ie. `localhost`). Such request recieves only 1 parameter (the invitee's email) and then carries out the same process as above.
