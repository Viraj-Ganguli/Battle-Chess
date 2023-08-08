import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Class;
import java.util.concurrent.TimeUnit;

public class Piece {
  Scanner in = new Scanner(System.in);
  private int color;
  private String art;
  private String symbol;
  private int HP = this.maxHP;
  private int xPos;
  private int yPos;
  private boolean isProtected;
  private int maxHP;
  private String margin = "  ";

  public Piece(int teamColor, int startingYPos, int startingXPos) {
    color = teamColor;
    xPos = startingXPos;
    yPos = startingYPos;
  }

  public int getX() {
    return xPos;
  }

  public int getY() {
    return yPos;
  }

  public int getColor() {
    return color;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String newSymbol) {
    symbol = newSymbol;
  }

  public void setArt(String s) {
    art = s;
  }

  public String getArt() {
    return art;
  }

  public void setX(int x) {
    xPos = x;
  }

  public void setY(int y) {
    yPos = y;
  }

  public String getMargin() {
    return margin;
  }

  public void setMargin(String m) {
    margin = m;
    this.refreshArt();
  }

  // parent function for inheritance
  public int calcLegalMove(String move, Piece[][] board) {
    return -1;
  }

  // returns array of useful information for testing a legal move
  public int[] calcLegalMoveHelper(String move, Piece[][] board) {
    boolean capture;
    int moveX = calcColumn(move.substring(0, 1));
    int moveY = 8 - Integer.parseInt(move.substring(1));
    int x = xPos - moveX;
    int y = yPos - moveY;
    if (moveX < 0 || moveX > 7 || moveY < 0 || moveY > 7 || (x == 0 && y == 0)) {
      return null;
    }
    Piece atLoc = board[moveY][moveX];
    if (atLoc == null)
      capture = false;
    else if (atLoc.getColor() == color)
      return null;
    else
      capture = true;
    int capint = capture ? 1 : 0;
    return new int[] { moveX, moveY, x, y, capint };
  }

  public static int calcColumn(String col) {
    int r = 0;

    if (col.equals("a")) {
      r = 0;
    } else if (col.equals("b")) {
      r = 1;
    } else if (col.equals("c")) {
      r = 2;
    } else if (col.equals("d")) {
      r = 3;
    } else if (col.equals("e")) {
      r = 4;
    } else if (col.equals("f")) {
      r = 5;
    } else if (col.equals("g")) {
      r = 6;
    } else if (col.equals("h")) {
      r = 7;
    }

    return r;
  }

  public void setHP(int hp) {
    this.HP = hp;
  }

  public void setMaxHP(int hp) {
    this.maxHP = hp;
  }

 
  public void sleep(int i) {
    try {
      Thread.sleep(i); // 1000 milliseconds = 1 second
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void takeDamage(int damage, Piece opp) {
    if (opp.isProtected == false) {
      System.out.println("The attack did " + damage + " damage to " + opp);
      sleep(2000);
      opp.HP -= damage;
    } else if (opp.isProtected == true) {
      System.out.println("The opponent is protected");
      sleep(2000);
    }

  }

  public void heal(int healing) {
    if (this.HP + healing > this.maxHP)
      this.HP = maxHP;
    else
      this.HP += healing;
    System.out.println("You healed " + healing + " hp");
    sleep(2000);
  }

  public int getHP() {
    return this.HP;
  }

  public void makeProtected() {
    isProtected = true;
  }

  public void makeVulnerable() {
    isProtected = false;
  }

  public boolean isProtected() {
    return isProtected;
  }

  // parent function for inheritance
  public void refreshArt() {

  }

  public boolean fight(Piece opponent) {
    int turn = 1;
    while (turn >= 1) {
      Main.cls();
      System.out.println("Combat Turn: " + turn);
      System.out.println(opponent.getArt());
      System.out.println((opponent.getColor() == 0 ? "White" : "Black") + " HP: " + opponent.getHP());
      this.setMargin("\t\t\t\t\t\t\t\t\t  ");
      System.out.println(this.getArt());
      this.setMargin("  ");
      System.out.println("\t\t\t\t\t\t\t\t\t" + (this.getColor() == 0 ? "White" : "Black") + " HP: " + this.getHP());
      if (turn % 2 != 0) {
        System.out.println("\n" + (this.getColor() == 0 ? "White" : "Black") + "'s turn" + "\n\n" + this.AbilityList());
      } else {
        System.out.println(
            "\n" + (opponent.getColor() == 0 ? "White" : "Black") + "'s turn" + "\n\n" + opponent.AbilityList());
      }
      System.out.println("\n Enter Move #: ");
      String input = in.nextLine();

      if (turn % 2 != 0) {

        if (input.equals("1")) {
          this.Ability1(opponent);
          turn++;
        } else if (input.equals("2")) {
          this.Ability2(opponent);
          turn++;
        } else if (input.equals("3")) {
          this.Ability3(opponent);
          turn++;
        } else if (input.equals("debugKill")) {
          opponent.setHP(0);
        } else {
          System.out.println("Enter a valid move");
          sleep(2000);
        }
      } else {

        if (input.equals("1")) {
          opponent.Ability1(this);
          turn++;
        } else if (input.equals("2")) {
          opponent.Ability2(this);
          turn++;
        } else if (input.equals("3")) {
          opponent.Ability3(this);
          turn++;
        } else if (input.equals("debugKill")) {
          this.setHP(0);
        } else {
          System.out.println("Enter a valid move");
          sleep(2000);
        }
      }
      if (opponent.getHP() == 69 || this.getHP() == 69) {
        System.out.println("Nice");
        sleep(2000);
      }
      if (opponent.getHP() <= 0) {
        return true;
      } else if (this.getHP() <= 0) {
        return false;
      }
    }
    /*
     * if (opponent.getHP() <= 0) {
     * return true;
     * } else {
     * return false;
     * }
     */
    // immortalizing this beautiful monument to my immense pain
    return false;
  }

  public String toString() {
    return (this.getColor() == 0 ? "white " : "black ") + this.getSymbol() + " \u001B[0m x:" + this.getX() + " y:"
        + this.getY();
  }

  public void Ability1(Piece opponent) {

  }

  public void Ability2(Piece opponent) {

  }

  // if child has no 3rd ability it will do nothing
  public void Ability3(Piece opponent) {
    System.out.println("This ability does nothing");
  }

  public String AbilityList() {
    return "abilities";
  }
}

class Rook extends Piece {
  public Rook(int teamColor, int startingXPos, int startingYPos) {
    super(teamColor, startingXPos, startingYPos);
    setHP(50);
    setMaxHP(50);
    refreshArt();
    if (teamColor == 0) {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♖");
    } else {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♜");
    }

  }

  public void refreshArt() {
    setArt(
            this.getMargin() + " |'-'-'| \n" +
            this.getMargin() + " |_____| \n" +
            this.getMargin() + "  |===|  \n" +
            this.getMargin() + "  |   |  \n" +
            this.getMargin() + "  |   |  \n" +
            this.getMargin() + "  )___(  \n" +
            this.getMargin() + " (=====) \n" +
            this.getMargin() + " }====={ \n" +
            this.getMargin() + "(_______)");
  }

  public int calcLegalMove(String move, Piece[][] board) {
    int[] coords = calcLegalMoveHelper(move, board);
    if (coords == null)
      return 0;
    int moveX = coords[0], moveY = coords[1], x = coords[2], y = coords[3];
    boolean capture = coords[4] == 1;
    if (y != 0 && x != 0)
      return 0;
    if (y > 0) {
      for (int i = this.getY() - 1; i > moveY; i--)
        if (board[i][this.getX()] != null)
          return 0;
    } else if (y < 0) {
      for (int i = this.getY() + 1; i < moveY; i++)
        if (board[i][this.getX()] != null)
          return 0;
    } else if (x > 0) {
      for (int i = this.getX() - 1; i > moveX; i--)
        if (board[this.getY()][i] != null)
          return 0;
    } else if (x < 0) {
      for (int i = this.getX() + 1; i < moveX; i++)
        if (board[this.getY()][i] != null)
          return 0;
    }
    if (capture)
      return 2;
    return 1;
  }

  public void Ability1(Piece opponent) {
    this.makeVulnerable();
    if (Math.random() < 0.90) {
      takeDamage((int) (Math.random() * (31) + 30), opponent);
    } else {
      System.out.println("Attack Missed");
      sleep(2000);
    }
  }

  public void Ability2(Piece opponent) {
    this.makeVulnerable();
    if (Math.random() < 0.50) {
      this.makeProtected();
    }
  }

  public String AbilityList() {
    return " Ability 1: Damage: 30-60  Accuracy: 90  \n Description: The rook uses their big sword to slice at the opponent \n \n Ability 2: \n Description: The rook uses its ability to be a castle to protect itself with only a 50% chance of success";
  }
}

class Bishop extends Piece {
  private int GodsPlanUse = 0;

  public Bishop(int teamColor, int startingXPos, int startingYPos) {
    super(teamColor, startingXPos, startingYPos);
    setHP(40);
    setMaxHP(40);
    refreshArt();
    if (teamColor == 0) {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♗");
    } else {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♝");
    }
  }

  public void refreshArt() {
    setArt(
            this.getMargin() + "    ()    \n" +
            this.getMargin() + "    /\\    \n" +
            this.getMargin() + "   //\\\\   \n" +
            this.getMargin() + "  (    )  \n" +
            this.getMargin() + "   )__(   \n" +
            this.getMargin() + "  /____\\  \n" +
            this.getMargin() + "   |  |   \n" +
            this.getMargin() + "   |  |   \n" +
            this.getMargin() + "  /____\\  \n" +
            this.getMargin() + " (======) \n" +
            this.getMargin() + " }======{ \n" +
            this.getMargin() + "(________)");
  }

  public int calcLegalMove(String move, Piece[][] board) {
    int[] coords = calcLegalMoveHelper(move, board);
    if (coords == null)
      return 0;
    int x = -coords[2], y = -coords[3];
    boolean capture = coords[4] == 1;

    if (!(Math.abs(x) == Math.abs(y)))
      return 0;
    for (int i = 1; i < Math.abs(x); i++) {
      if (y > 0 && x > 0 && board[this.getY() + i][this.getX() + i] != null) {
        return 0;
      }
      if (y > 0 && x < 0 && board[this.getY() + i][this.getX() - i] != null) {
        return 0;
      }
      if (y < 0 && x > 0 && board[this.getY() - i][this.getX() + i] != null) {
        return 0;
      }
      if (y < 0 && x < 0 && board[this.getY() - i][this.getX() - i] != null) {
        return 0;
      }
    }
    if (capture)
      return 2;
    return 1;
  }

  public void Ability1(Piece opponent) {
    GodsPlanUse++;
    if ((GodsPlanUse % 2 == 1) && (Math.random() < 0.95)) {
      takeDamage((int) (Math.random() * (61) + 15), opponent);
    } else {
      System.out.println("Move Failed!");
    }
  }

  public void Ability2(Piece opponent) {
    GodsPlanUse++;
    this.heal((int) (Math.random() * (11) + 10));
  }

  public void Ability3(Piece opponent) {
    // super();
    GodsPlanUse++;
  }

  public String AbilityList() {
    return " Ability 1: Damage: 15-75  Accuracy: 95  \n Description: Uses the power of god to smite the enemy, but god gets tired (Cannot be used twice in a row) \n \n Ability 2: Healing 10-20 \n Description: The Bishop asks god to heal them";
  }
}

class Queen extends Piece {
  private int overkillUse = 1;

  public Queen(int teamColor, int startingXPos, int startingYPos) {
    super(teamColor, startingXPos, startingYPos);
    setHP(75);
    setMaxHP(75);
    refreshArt();
    if (teamColor == 0) {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♕");
    } else {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♛");
    }
  }

  public void refreshArt() {
    setArt(
            this.getMargin() + "    ()    \n" +
            this.getMargin() + " .-:--:-. \n" +
            this.getMargin() + "  \\____/  \n" +
            this.getMargin() + "  {====}  \n" +
            this.getMargin() + "   )__(   \n" +
            this.getMargin() + "  /____\\  \n" +
            this.getMargin() + "   |  |   \n" +
            this.getMargin() + "   |  |   \n" +
            this.getMargin() + "   |  |   \n" +
            this.getMargin() + "   |  |   \n" +
            this.getMargin() + "  /____\\  \n" +
            this.getMargin() + " (======) \n" +
            this.getMargin() + " }======{ \n" +
            this.getMargin() + "(________)");
  }

  public int calcLegalMove(String move, Piece[][] board) {
    int[] coords = calcLegalMoveHelper(move, board);
    if (coords == null)
      return 0;
    int moveX = coords[0], moveY = coords[1], x = -coords[2], y = -coords[3];
    boolean capture = coords[4] == 1;

    if (Math.abs(x) == Math.abs(y)) {

      for (int i = 1; i < Math.abs(x); i++) {
        if (y > 0 && x > 0 && board[this.getY() + i][this.getX() + i] != null) {
          return 0;
        }
        if (y > 0 && x < 0 && board[this.getY() + i][this.getX() - i] != null) {
          return 0;
        }
        if (y < 0 && x > 0 && board[this.getY() - i][this.getX() + i] != null) {
          return 0;
        }
        if (y < 0 && x < 0 && board[this.getY() - i][this.getX() - i] != null) {
          return 0;
        }
      }
    } else if ((x == 0 & y != 0) || (x != 0 && y == 0)) {
      x *= -1;
      y *= -1;
      if (y != 0 && x != 0)
        return 0;
      if (y > 0) {
        for (int i = this.getY() - 1; i > moveY; i--)
          if (board[i][this.getX()] != null)
            return 0;
      } else if (y < 0) {
        for (int i = this.getY() + 1; i < moveY; i++)
          if (board[i][this.getX()] != null)
            return 0;
      } else if (x > 0) {
        for (int i = this.getX() - 1; i > moveX; i--)
          if (board[this.getY()][i] != null)
            return 0;
      } else if (x < 0) {
        for (int i = this.getX() + 1; i < moveX; i++)
          if (board[this.getY()][i] != null)
            return 0;
      }
    }
    if (capture)
      return 2;
    return 1;
  }

  public void Ability1(Piece opponent) {
    if (Math.random() < 0.90) {
      takeDamage((int) (Math.random() * (21) + 40), opponent);
    } else {
      System.out.println("This Attack Missed!");
      sleep(2000);

    }
  }

  public void Ability2(Piece opponent) {
    if (overkillUse == 1 && Math.random() < 0.95) {
      takeDamage(100000000, opponent);
      overkillUse--;
    } else {
      System.out.println("The attack failed");
    }
  }

  public void Ability3(Piece opponent) {
    this.heal((int) (Math.random() * (21) + 40));
  }

  public String AbilityList() {
    return " Ability 1: Damage: 40-60  Accuracy: 90  \n Description: The queen uses her sharp crown to jab at the opponent \n \n Ability 2: Damage: 100000000 Accuracy: 95 \n Description: The Queen unleashes her full might smiting the opponent with the wrath of god (Can only be used once) \n \n Ability 3: Heals the queen for 20-40 hp";
  }
}

class King extends Piece {
  public King(int teamColor, int startingXPos, int startingYPos) {
    super(teamColor, startingXPos, startingYPos);
    setHP(100);
    setMaxHP(100);
    refreshArt();
    if (teamColor == 0) {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♔");
    } else {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♚");
    }
  }

  public void refreshArt() {
    setArt(
            this.getMargin() + "    _:_    \n" +
            this.getMargin() + "   '-.-'   \n" +
            this.getMargin() + "  __.'.__  \n" +
            this.getMargin() + " |_______| \n" +
            this.getMargin() + "  \\=====/  \n" +
            this.getMargin() + "   )___(   \n" +
            this.getMargin() + "  /_____\\  \n" +
            this.getMargin() + "   |   |   \n" +
            this.getMargin() + "   |   |   \n" +
            this.getMargin() + "   |   |   \n" +
            this.getMargin() + "   |   |   \n" +
            this.getMargin() + "   |   |   \n" +
            this.getMargin() + "  /_____\\  \n" +
            this.getMargin() + " (=======) \n" +
            this.getMargin() + " }======={ \n" +
            this.getMargin() + "(_________)");
  }

  public int calcLegalMove(String move, Piece[][] board) {
    int[] coords = calcLegalMoveHelper(move, board);
    if (coords == null)
      return 0;
    int x = Math.abs(coords[2]), y = Math.abs(coords[3]);
    boolean capture = coords[4] == 1;

    if (x > 1 || y > 1)
      return 0;
    if (capture)
      return 2;
    return 1;
  }

  int recoverUse = 1;

  public void Ability1(Piece opponent) {
    takeDamage((int) (Math.random() * 100), opponent);
  }

  public void Ability2(Piece opponent) {
    if (recoverUse > 0) {
      this.heal(100);
      recoverUse--;
    } else {
      System.out.println("This attack failed");
    }
  }

  public void Ability3(Piece opponent) {
    if (Math.random() < 0.95) {
      int a = (int) (Math.random() * 21 + 30);
      takeDamage(a, opponent);
      this.heal((int) (a * 0.5));
    } else {
      System.out.println("The attack missed");
      sleep(2000);
    }
  }

  public String AbilityList() {
    return " Ability 1: Damage: 0-100  Accuracy: 100  \n Description: The king uses his rich person staff to pummel at the opponent. . . sometimes it works \n \n Ability 2: Healing: 100 \n Description: The king downs a bottle of Fiji water and regains some vigor (he only has one bottle) \n \n Ability 3: Damage: 30-50 Accuracy: 95 \n Uses the power of capitalism to buy (steal) some HP from an oppoent (half the ammount damaged)";
  }
}

class Pawn extends Piece {
  public Pawn(int teamColor, int startingXPos, int startingYPos) {
    super(teamColor, startingXPos, startingYPos);
    setHP(20);
    setMaxHP(20);
    refreshArt();
    if (teamColor == 0) {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♙");
    } else {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♟︎");
    }
  }

  public void refreshArt() {
    setArt(
            this.getMargin() + "   __   \n" +
            this.getMargin() + "  /  \\  \n" +
            this.getMargin() + "  \\__/  \n" +
            this.getMargin() + " /____\\ \n" +
            this.getMargin() + "  |  |  \n" +
            this.getMargin() + "  |__|  \n" +
            this.getMargin() + " (====) \n" +
            this.getMargin() + " }===={ \n" +
            this.getMargin() + "(______)");
  }

  public int calcLegalMove(String move, Piece[][] board) {
    int[] coords = calcLegalMoveHelper(move, board);
    if (coords == null)
      return 0;
    int x = Math.abs(coords[2]), y = coords[3];
    boolean capture = coords[4] == 1;

    int multi = this.getColor() == 0 ? 1 : -1;

    if (!capture && x == 0 && (y == multi || (this.getY() == (this.getColor() == 0 ? 6 : 1) && y == (multi * 2))))
      return 1;
    else if (capture && x == 1 && y == multi)
      return 2;
    return 0;
  }

  public void promote(ArrayList<Piece> pieces, Piece[][] board) {
    Queen newQ = new Queen(this.getColor(), this.getY(), this.getX());
    pieces.set(pieces.indexOf(this), newQ);
    board[this.getY()][this.getX()] = newQ;
  }

  public void Ability1(Piece opponent) {
    if (Math.random() < 0.75) {
      takeDamage((int) (Math.random() * (21) + 20), opponent);
    } else {
      System.out.println("The attack missed");
      sleep(2000);
    }
  }

  public void Ability2(Piece opponent) {
    int a = (int) (Math.random() * 100);
    if (a < 5) {
      takeDamage(100000, opponent);
    } else {
      System.out.println("Attack Missed");
      takeDamage(10000, this);
    }
  }

  public String AbilityList() {
    return " Ability 1: Damage: 25-45  Accuracy: 75  \n Description: The classic peasant punch  \n \n Ability 2: Damage: 10000  Accuracy: 5 \n Description: the pawn channels all of its rage into one attack, if the pawn misses, it dies";
  }
}

class Knight extends Piece {
  int dodgeUses = 1;

  public Knight(int teamColor, int startingXPos, int startingYPos) {
    super(teamColor, startingXPos, startingYPos);
    setHP(30);
    setMaxHP(30);
    refreshArt();
    if (teamColor == 0) {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♘");
    } else {
      setSymbol("\u001B[38;5;00m\u001B[48;5;255m♞");
    }
  }

  public void refreshArt() {
    setArt(
            this.getMargin() + "  (\\=,   \n" +
            this.getMargin() + " //  .\\  \n" +
            this.getMargin() + "(( \\_  \\ \n" +
            this.getMargin() + " ))  `\\_)\n" +
            this.getMargin() + "(/     \\ \n" +
            this.getMargin() + " | _.-'| \n" +
            this.getMargin() + "  )___(  \n" +
            this.getMargin() + " (=====) \n" +
            this.getMargin() + " }====={ \n" +
            this.getMargin() + "(_______)");
  }

  public int calcLegalMove(String move, Piece[][] board) {
    int[] coords = calcLegalMoveHelper(move, board);
    if (coords == null)
      return 0;
    int x = Math.abs(coords[2]), y = Math.abs(coords[3]);
    boolean capture = coords[4] == 1;

    if ((x == 2 && y == 1) || (y == 2 && x == 1)) {
      if (capture)
        return 2;
      return 1;
    }
    return 0;
  }

  public void Ability1(Piece opponent) {
    this.makeVulnerable();// spelling error but idk if fixing it will cause more problems
    int a = 0;
    for (int i = 0; i < 3; i++) {
      if (Math.random() < 0.75) {
        a += 15;
      }
      if (a == 0) {
        System.out.println("This attack missed");
        sleep(2000);
      }
    }

    takeDamage(a, opponent);
  }

  public void Ability2(Piece opponent) {
    this.makeVulnerable();
    if (dodgeUses % 3 == 0 && Math.random() < 0.90 && dodgeUses <= 3) {
      this.makeProtected();
      dodgeUses++;
    } else if (dodgeUses % 3 == 1 && Math.random() < 0.50 && dodgeUses <= 3) {
      this.makeProtected();
      dodgeUses++;
    } else if (dodgeUses % 3 == 2 && Math.random() < 0.20 && dodgeUses <= 3) {
      this.makeProtected();
      dodgeUses++;
    } else {
      System.out.println("This ability failed");
    }
  }

  public String AbilityList() {
    return " Ability 1: Damage: 15  Accuracy: 75  \n Description: The horse makes a wild charge at the opponent and attacks three times \n \n Ability 2: \n Description: the knight uses its mobility to attempt to dodge attacks, can only be used a max of 3 times, reliability reduces each time";
  }
}
