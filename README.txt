Dependencies:
- Java Development Kit (JDK): Version 25
- JavaFX
  - Handled by pom.xml
- Java IDE

Installation Instructions:
1) You can either:
a) Download zip file from https://github.com/Pootis27/MachineProblemActivity. Then extract to be opened by IDE
b) Clone the repository using your IDE’s Git integration: https://github.com/Pootis27/MachineProblemActivity
2) Run on either Launcher or Main class to start the game


*\ Introduction /*
Welcome to who wants to be a millionaire. A game where each question you get right in a row increases the money prize you can get (monopoly money in this case of course).
Of course, it's not just endless trivia question. We feature:
- Lifelines to save you from a particularly hard question
- Cameos from Miku, Gman, some random turtle dude, and a woman [not really. It's just one of the devs badly imitating them and using AI, and Miku being text to speech]
- Music which are definitely not ours
- Endless fun with randomized options and questions [not really endless]

*\ Features /*
- Questions
  - Randomized for each stage with randomized options based on the question's category
- Score Tracker
  - Visualized as a score ladder which fills up as player gets more questions right
  - Indicates the checkpoints
- 4 Lifelines
  - 50/50: Hides 2 incorrect answer
  - Call A Friend: Chooses a random person from a predefined list and plays their corresponding audio to tell the answer. Friend's Answer has 70% chance to be correct
  - Ask the Audience: Simulate 100 audience voting each having 40% chance of voting the correct answer. Then display a graph of their votes
  - Landmine Checker: Choose an option, and it will indicate if that answer is right or wrong
- Safe Haven
  - Checkpoint for the player which guarantees they get the money at safe haven if they choose wrong in subsequent questions
- Forfeit
  - End the game prematurely and get the money at that stage instead
- Audio
  - Background Music that plays and pauses at appropriate times
  - Stinger sound effects for correct and wrong
  - Music for main menu and end screen



MACHINE PROBLEM FINALS: Who Wants to be a Millionaire?
Developed By:
- Chris Euan de Leon
- Luke Andrei Gravador
- John Steven Cabugao