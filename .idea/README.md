# 🕹️ Smart Hangman (JavaFX + AI Solver)

A robust desktop application that modernizes the classic Hangman game using **JavaFX** and a **Model-View-Controller (MVC)** architecture. 

Beyond standard gameplay, this project features an integrated **AI Solver** that uses a constraint satisfaction algorithm to assist the player, calculating the statistically most probable letter based on the current word pattern.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-4285F4?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

## Key Features

* **UI:** Built with **JavaFX** and custom Canvas drawing for dynamic rendering of the hangman figure.
* **MVC Architecture:** Strict separation of concerns:
    * **Model:** Handles game logic, state management, and file I/O.
    * **View:** JavaFX visual components and dynamic Canvas updates.
    * **Controller:** Manages user input and bridges data flow.
* **Algorithmic Solver (AI):** A "Get Hint" feature that analyzes the dictionary in real-time to suggest the optimal next move.
* **Build Automation:** Fully managed with **Apache Maven** for dependency handling and lifecycle management.
* **Unit Testing:** (Optional: Add this if you added tests) JUnit 5 integration for testing game logic.

## How the "AI" Works

The Solver is not a neural network, but a **Constraint Satisfaction Algorithm**. When asked for a hint, the system performs the following steps in O(N) time:

1.  **Filter:** It scans the dictionary and eliminates words that do not match the current length or known character pattern (e.g., `_ P P L _`).
2.  **Prune:** It removes words containing letters known to be incorrect.
3.  **Frequency Analysis:** It calculates the frequency of every remaining letter in the valid candidate list.
4.  **Probability Ranking:** It suggests the letter with the highest occurrence probability that hasn't been guessed yet.

## 🚀 How to Run

### Prerequisites
* JDK 21 or higher
* Maven 3.8+

### Installation
1.  **Clone the repository**
    ```bash
    git clone [https://github.com/Akhilesh271/Hangman.git](https://github.com/Akhilesh271/Hangman.git)
    cd HangmanPro
    ```

2.  **Build and Run**
    ```bash
    mvn clean javafx:run
    ```

## 📂 Project Structure

```text
src/main/java/com/hangman/
├── HangmanApp.java      # Entry point & View setup
├── HangmanModel.java    # Game State & Logic (Model)
├── SolverAI.java        # The Probability Algorithm
└── Word.java            # Resource Loader