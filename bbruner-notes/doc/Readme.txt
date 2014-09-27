**** bbruner-notes README ****

When the app starts you will be asked to provide an email address. This
was done because I did not have time to think of another way to allow
use of the app and ask for an email address later.

** App Layout **

After providing an email address you will probably see an action bar with a few
possible selections. You can switch between viewing archived, all, and main
todos. You can also change your email address. 

To create a new todo make sure you are in the main view - where there is a 
text box and a button that says 'new'. Enter the name of the todo in the 
text  box and hit new. 

The new todo has a checkbox and an empty star. The empty star means the todo is
unfinished. Select this todo by tapping the check box. You will notice that
your action bar changed. The new options available allow you to move the
selected to dos between archived and main todos, mark them as finished or 
unfinished, or email them.

In summary there are three views you can cycle through by using the main action
bar - main, archive, and all. Selecting one or more todo items will change your
action bar to give options that affect the selected todos.


** Icons **
Note - not all icons may be displayed - depending on the size of your screen

CAB (action bar when check box is filled)
*garbage can - delete
*sd-card - send to archive
*full star - mark as finished
*empty star - mark as unfinished
*... - Email selected

AB (action bar normally present)
*Folder - move to all to do list
*sd-card - move to archived to do list
*price tag - move to main to do list
*... - show statisics or change email



**** Bugs ****

There may be UI related bugs that I have no thought to test or encountered yet.
The code base is bloated with much redundant code - this is a result of quickly
going from knowing nothing of android/java to programming this app. When I 
began to realize I had made methodological mistakes they had already snowballed
into huge problems that would take too much time and effort to fix - that is
if I wanted to make the dead line for the project. 

It will be very clear when reading the UML that MainActivity, ArchiveActivity,
and AllActivity should have been combined into one activity as they all
do he exact same thing with an ever so slightly different view. I would love
to change this, but time  does not allow it I guess.


**** Sources ****
The email functionality was copied and pasted from stack overflow, credit belongs to fiXedd in this post at 
http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application

The custom adapter is a loose reflection of the custom adapter from Lars Vogel in his tutorial at
http://www.vogella.com/tutorials/AndroidListView/article.html

The file io classes are copied and pasted from the lonely twitter app presented in CMPUT 301 lab. It was
taken from guana's github at https://github.com/guana/LonelyTwitter3

All other API calls are attributed to androids documentation and countless google searches 
