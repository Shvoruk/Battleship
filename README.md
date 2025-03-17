<style>
.fixed-logo {
    font-family: monospace;
    white-space: pre;
    color: deepskyblue;
}
</style>

<p class="fixed-logo">
██████╗  █████╗ ████████╗████████╗██╗     ███████╗███████╗██╗  ██╗██╗██████╗  
██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║     ██╔════╝██╔════╝██║  ██║██║██╔══██╗  
██████╔╝███████║   ██║      ██║   ██║     █████╗  ███████╗███████║██║██████╔╝  
██╔══██╗██╔══██║   ██║      ██║   ██║     ██╔══╝  ╚════██║██╔══██║██║██╔═══╝  
██████╔╝██║  ██║   ██║      ██║   ███████╗███████╗███████║██║  ██║██║██║      
╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝      
</p>


## ⚓️ About the Project

**Battleship** is a console-based strategy game built with **Java 23**, designed to recreate the classic Battleship experience while introducing enhanced gameplay mechanics. The game supports both:
- **Single-player mode** (against computer with randomised shot selection)
- **Multiplayer mode** (two players on the same machine)

The game features **flexible board sizes and customisable ship configurations**. If you wish to introduce a new game mode, you can simply extend the `GameMode` class and configure its board size and ship fleet to suit your preferences.

Currently, the game offers:

- **Sea Mode (10×10 board, standard fleet)**
- **Ocean Mode (20×20 board, extended fleet for longer battles)**

This project is **still under development**, and new changes may be introduced in future updates. However, the current version is fully playable and implements all core mechanics.

![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)

## 🎣 Features

- Turn-based gameplay with an interactive board display
- Two game modes: Sea (10×10) and Ocean (20×20)
- Single-player mode (against computer with random shot selection)
- Multiplayer mode (two players on the same machine)
- Replay functionality for reviewing entire matches move-by-move
- "Extra Turn on Hit" rule, allowing a player to fire again if they hit a ship
- Packaged with Maven and Docker, the game is easy to set up and deploy across different systems.

## 🛠️ Installation & Setup

### Requirements

Before running the game, ensure you have the following installed on your machine:


- ![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white&style=flat) **Java Development Kit (JDK 23) (for running the game directly)**
- ![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white&style=flat) **Apache Maven (for building the project)**
- ![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white&style=flat) **Docker (if you choose to use the Docker setup)**
- ![Git](https://img.shields.io/badge/Git-F05032?logo=git&logoColor=white&style=flat) **Git (for cloning the repository)**

### Using Maven

To get started with the game, follow these steps:

1. **Clone the Repository**
   ```sh
   git clone https://github.com/Shvoruk/Battleship.git
2. **Navigate to the Project directory**
   ```sh
   cd Battleship
3. **Build the Project**
   ```sh
   mvn clean package
4. **Run the Game**
   ```sh
   java -jar target/Battleship-1.0.0.jar

### Using Docker

3. **Build the Docker Image**
   ```sh
   docker build -t battleship .
4. **Run the Container**
   ```sh
   docker run -it battleship
