package chess;

import chess.Database.SQLConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;

public class Controller {
    private CardLayout screens;
    private JPanel homeContainer;
    private BoardView boardView;
    private AccountPageView userPage;
    private LeaderboardView leaderboardView;
    TopBarView topBarView;
    private Board boardModel;
    private Game game;
    private boolean boardActive;

    private boolean enteringName;

    String activeUser;

    public Controller(BoardView boardView, Board boardModel, Game game, TopBarView topBarView, CardLayout screens, JPanel homeContainer, AccountPageView accountPage, LeaderboardView leaderboard) {
        this.boardView = boardView;
        this.userPage = accountPage;
        this.leaderboardView = leaderboard;
        this.boardModel = boardModel;
        this.game = game;
        this.topBarView = topBarView;
        this.screens = screens;
        this.homeContainer = homeContainer;
        boardActive = true;

        enteringName = false;

        setUpViews();
    }

    public void setUpViews() {
        setUpSquares();

        setUpButtons();
    }

    public void setUpSquares() {
        int i = 0;
        for (SquareView squareView: boardView.getSquares()) {
            final int finalI = i;
            squareView.addActionListener(new ActionListener() {
                Square square = boardModel.getSquare(finalI);
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (boardActive) {
                        game.squareClicked(square);
                    }
                }
            });
            i++;
        }
    }

    public void setUpButtons() {

        topBarView.getVSMode().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardActive = true;
                game.setUpGame();
                screens.show(homeContainer, "board");
            }
        });

        topBarView.getAIMode().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardActive = true;
                game.setUpAIGame();
                screens.show(homeContainer, "board");
            }
        });

        topBarView.getEnterUsername().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!enteringName) {
                    activeUser = topBarView.getEnterUsername().getText();
                }
                try {
                    if (enteringName) {
                        return;
                    } else if (SQLConnection.getInstance().findAccount(activeUser).isEmpty()) {
                        topBarView.getCreateAccount().setEnabled(true);
                        topBarView.getLogin().setEnabled(false);
                    } else {
                        topBarView.getLogin().setEnabled(true);
                        topBarView.getCreateAccount().setEnabled(false);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        topBarView.getGameText().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = topBarView.getGameText().getText();
                try {
                    if (!SQLConnection.getInstance().findAccount(name).isEmpty()) {

                        if (topBarView.onFirstPlayer) {
                            game.getPlayerOne().setName(name);
                            topBarView.getGameText().setText("Enter Player Two");
                            topBarView.getAIMode().setEnabled(true);
                            topBarView.onFirstPlayer = false;
                        } else {
                            game.getPlayerTwo().setName(name);
                            topBarView.getGameText().setText("Ready To Start!");
                            topBarView.getGameText().setEnabled(false);
                            topBarView.getVSMode().setEnabled(true);
                        }
                    } else {
                        if (topBarView.onFirstPlayer) {
                            topBarView.getGameText().setText("Enter Player One");
                        } else {
                            topBarView.getGameText().setText("Enter Player Two");
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        topBarView.getLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (userPage.loadAccount(activeUser)) {
                        topBarView.getAccount().setEnabled(true);
                        topBarView.getCreateAccount().setEnabled(false);
                        topBarView.getLogin().setEnabled(false);
                        topBarView.getAccount().setText(activeUser);
                        topBarView.getEnterUsername().setText("Enter Username");
                    } else {
                        System.out.println("User not found");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        topBarView.getCreateAccount().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!enteringName) {
                        enteringName = true;
                        topBarView.getEnterUsername().setText("Enter name");
                    } else if (SQLConnection.getInstance().createAccount(activeUser, topBarView.getEnterUsername().getText())) {
                        userPage.loadAccount(activeUser);
                        topBarView.getAccount().setEnabled(true);
                        topBarView.getCreateAccount().setEnabled(false);
                        topBarView.getLogin().setEnabled(false);
                        topBarView.getAccount().setText(activeUser);
                        topBarView.getEnterUsername().setText("Enter Username");
                        enteringName = false;
                    } else {
                        System.out.println("Account already exits");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        topBarView.getLeaderboard().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    leaderboardView.loadLeaderboard();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                screens.show(homeContainer, "leaderboard");
            }
        });

        topBarView.getAccount().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screens.show(homeContainer, "user");
            }
        });

        userPage.getRemoveAccount().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLConnection.getInstance().removeAccount(activeUser);
                    screens.show(homeContainer, "board");
                    topBarView.getAccount().setEnabled(false);
                    topBarView.getCreateAccount().setEnabled(false);
                    topBarView.getLogin().setEnabled(false);
                    topBarView.getAccount().setText("Account");
                    topBarView.getEnterUsername().setText("Enter Username");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void activateBoard() {
        boardActive = true;
        for (SquareView square : boardView.getSquares()) {
            square.setEnabled(true);
        }
    }

    public void freezeBoard() {
        boardActive = false;
        for (SquareView square : boardView.getSquares()) {
            square.setEnabled(true);
        }
    }
}
