## Enduro Atari 2600 – Final Assessment Project for the course T164-54 Object-Oriented Programming, taught by Professor Gilson Pereira.

Enduro is a classic racing game released for the Atari 2600.

In the original game, the player drives continuously along a highway, overtaking a required number of cars before the day ends. As time progresses, environmental changes such as fog, snow, sunlight glare, and nightfall increase the difficulty and reduce visibility.

<img width="700" alt="image" src="https://github.com/user-attachments/assets/88dd0bdf-8c2c-44d9-872b-5d277868e956" />

This project is a recreation of Enduro developed in Java, using Swing and AWT (Abstract Window Toolkit) for graphical rendering.
Developed by:
Ana Clara and Inácio Araripe (@github)

While inspired by the original mechanics, our version introduces a modified scoring system:
- The player loses immediately when colliding with another car
- Collisions with the track walls cause significant speed reduction
The scoring system is continuous:
- Points increase proportionally to the player's speed
- Higher speed → faster score progression
- Lower speed → slower score accumulation
- The goal is to achieve the highest possible score

<img width="700" alt="Captura de tela 2026-03-03 222442" src="https://github.com/user-attachments/assets/3a2d1dcc-bbd9-486a-8553-269bf597e1b6" />

This project was developed based on instructional material focused on building simple games using standard JDK libraries, especially Swing and AWT.
The main technical references were Asteroids and Space Invaders .
Asteroids served as a strong foundation due to: smooth rendering of multiple on-screen objects, continuous movement logic, use of game loops, handling multiple entities with different sizes, game loop implementation, object modeling, collision handling and class organization.


An additional requirement from the professor was the implementation of a ranking system.
This feature includes:
- File manipulation in Java
- Persistent storage using a .TXT file
- A mini-form integrated into the Swing window
- Score saving and ranking display

<img width="700" alt="Captura de tela 2026-03-03 223231" src="https://github.com/user-attachments/assets/0765ab9c-74eb-4edb-a9a6-3d78166bb958" />

## How to Run the Project ▶ 
Copy all project files.
Create a new Java project in IntelliJ IDEA.
Paste the files into the automatically generated src folder.
Run the main game class.
