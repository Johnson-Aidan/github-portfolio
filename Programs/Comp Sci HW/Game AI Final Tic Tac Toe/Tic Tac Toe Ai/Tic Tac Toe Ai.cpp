#include <iostream>
#include <climits>

using namespace std;

char board[3][3] = { {'1','2','3'},{'4','5','6'},{'7','8','9'} };
int choice;
int row, column;
char turn = 'X';
bool draw = false;

// Function prototypes
void display_board();
void game_start();
void mark_board();
bool gameover();
bool testforgameover();
int minimax(int depth, bool is_max);
void ai_turn();
int check_board();


// Program Main Method
int main() {
    bool gameOver = true;
    cout << "\t T I C K -- T A C -- T O E\t\t\t";
    cout << "\n\t   FIGHT AN IMPOSSIBLE AI\n\t\t\t";
    cout << "\n\n\tPLAYER - 1 [X]\t AI - 2 [O]\n\n";
    display_board();

    while (gameOver) {
        game_start();
        gameOver = gameover();
        if (turn == 'X' && gameOver) {
            turn = 'O';
        }
        else if (turn == 'O' && gameOver) {
            turn = 'X';
        }
    }
    if (turn == 'X' && draw == false) {
        cout << "\n\nCongratulations! You have won the game\n\n";
        cin.get();
    }
    else if (turn == 'O' && draw == false) {
        cout << "\n\Git Gud! AI has won the game\n\n";
        cin.get();
    }
    else {
        cout << "\n\nGAME DRAW!!!\n\n";
    }

    cout << "Press Enter to close program! Bye bye! - Aidan and Liz :)";
    cin.get();

}


// Function to show the current status of the gaming board
void display_board() {
    cout << "\n\t\t     |     |     \n";
    cout << "\t\t  " << board[0][0] << "  |  " << board[0][1] << "  |  " << board[0][2] << " \n";
    cout << "\t\t_____|_____|_____\n";
    cout << "\t\t     |     |     \n";
    cout << "\t\t  " << board[1][0] << "  |  " << board[1][1] << "  |  " << board[1][2] << " \n";
    cout << "\t\t_____|_____|_____\n";
    cout << "\t\t     |     |     \n";
    cout << "\t\t  " << board[2][0] << "  |  " << board[2][1] << "  |  " << board[2][2] << " \n";
    cout << "\t\t     |     |     \n";
}

// Function to get the player input and update the board
void game_start() {
    if (turn == 'X') {
        cout << "\n\tPlayer - 1 [X] turn : ";
        cin >> choice;
        mark_board();
        display_board();
    }
    else {
        // Player 2 (AI) turn
        cout << "\n\tAI - 2 [O] turn : ";
        ai_turn();
        display_board();
    }
}

// Function to mark the board based on the player's choice
void mark_board() {
    switch (choice) {
    case 1: row = 0;
        column = 0;
        break;
    case 2: row = 0;
        column = 1;
        break;
    case 3: row = 0;
        column = 2; break;
    case 4: row = 1;
        column = 0;
        break;
    case 5: row = 1;
        column = 1;
        break;
    case 6: row = 1;
        column = 2;
        break;
    case 7: row = 2;
        column = 0;
        break;
    case 8: row = 2;
        column = 1;
        break;
    case 9: row = 2;
        column = 2;
        break;
    default:
        cout << "Invalid Move";
    }

    if (board[row][column] != 'X' && board[row][column] != 'O') {
        // Updating the position for 'X' or 'O' symbol if it is not already occupied
        board[row][column] = turn;
    }
    else {
        // If input position already filled
        cout << "Box already filled!\n Please choose another!!\n\n";
        game_start();
    }
}

// Function to get the game status e.g. GAME WON, GAME DRAW, GAME IN CONTINUE MODE
bool gameover() {
    // Checking the win for Simple Rows and Simple Column
    for (int i = 0; i < 3; i++)
        if (board[i][0] == board[i][1] && board[i][0] == board[i][2] || board[0][i] == board[1][i] && board[0][i] == board[2][i])
            return false;

    // Checking the win for both diagonals
    if (board[0][0] == board[1][1] && board[0][0] == board[2][2] || board[0][2] == board[1][1] && board[0][2] == board[2][0])
        return false;

    // Checking if the game is in continue mode or not
    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            if (board[i][j] != 'X' && board[i][j] != 'O')
                return true;

    // Checking if the game is already a draw
    draw = true;
    return false;
}

bool testforgameover() {
    // Checking the win for Simple Rows and Simple Column
    for (int i = 0; i < 3; i++)
        if (board[i][0] == board[i][1] && board[i][0] == board[i][2] || board[0][i] == board[1][i] && board[0][i] == board[2][i])
            return false;

    // Checking the win for both diagonals
    if (board[0][0] == board[1][1] && board[0][0] == board[2][2] || board[0][2] == board[1][1] && board[0][2] == board[2][0])
        return false;

    // Checking if the game is in continue mode or not
    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            if (board[i][j] != 'X' && board[i][j] != 'O')
                return true;

    // Checking if the game is already a draw
    return false;
}

// Minimax algorithm
int minimax(int depth, bool is_max) {
    int score = 0;
    //Check if the game is over.  If not, proceed with the minimax algorithm    
    if (!testforgameover()) {
        score = check_board();
    }
    else {
        int best_score = is_max ? INT_MIN : INT_MAX;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 'X' && board[i][j] != 'O') {
                    char original_value = board[i][j];

                    // Make the move
                    board[i][j] = is_max ? 'O' : 'X';

                    // Recursively call minimax
                    int current_score = minimax(depth + 1, !is_max);

                    // Undo the move
                    board[i][j] = original_value;

                    // Update the best score
                    best_score = is_max ? max(best_score, current_score) : min(best_score, current_score);
                }
            }
        }

        score = best_score;
    }

    return score;
}

// Function to find the best move for the AI
void ai_turn() {
    int best_val = INT_MIN;
    row = -1;
    column = -1;

    // Traverse all cells and find the move with the highest minimax value
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (board[i][j] != 'X' && board[i][j] != 'O') {
                char original_value = board[i][j];

                // Make the move
                board[i][j] = 'O';

                // Find the minimax value for this move
                int move_val = minimax(0, false);

                // Undo the move
                board[i][j] = original_value;

                // Update the best move if the current move is better
                if (move_val > best_val) {
                    row = i;
                    column = j;
                    best_val = move_val;
                }
            }
        }
    }

    board[row][column] = 'O';
}

// Function to check the current board state
int check_board() {
    // If O wins, we return 1, for X we return -1, and 0 returns for draw
    for (int i = 0; i < 3; i++) {
        // Check rows and columns
        if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
            if (board[i][0] == 'O') return 1;
            else if (board[i][0] == 'X') return -1;
        }
        if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
            if (board[0][i] == 'O') return 1;
            else if (board[0][i] == 'X') return -1;
        }
    }

    // Check diagonals
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
        if (board[0][0] == 'O') return 1;
        else if (board[0][0] == 'X') return -1;
    }
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
        if (board[0][2] == 'O') return 1;
        else if (board[0][2] == 'X') return -1;
    }


    return 0;
}