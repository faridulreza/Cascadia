package com.example.cascadia.scoring;

import com.example.cascadia.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.LinkedList;;

public class ScoreViewerController {
    public TableView scoreTable;
    public TableColumn player1Column;
    public TableColumn Player2Column;
    public TableColumn Description;
    public TableColumn scoreType;

    public class Row{
        public String type;
        public String player1;
        public String player2;
        public  String description;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPlayer1() {
            return player1;
        }

        public void setPlayer1(String player1) {
            this.player1 = player1;
        }

        public String getPlayer2() {
            return player2;
        }

        public void setPlayer2(String player2) {
            this.player2 = player2;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


    }



    @FXML
    public void initialize() {
        player1Column.setCellValueFactory(new PropertyValueFactory<Row, String>("player1"));
        Player2Column.setCellValueFactory(new PropertyValueFactory<Row, String>("player2"));
        Description.setCellValueFactory(new PropertyValueFactory<Row, String>("description"));
        scoreType.setCellValueFactory(new PropertyValueFactory<Row, String>("type"));

        scoreType.prefWidthProperty().bind(scoreTable.widthProperty().multiply(.24));
        player1Column.prefWidthProperty().bind(scoreTable.widthProperty().multiply(.18));
        player1Column.setStyle("-fx-alignment: CENTER;");
        Player2Column.prefWidthProperty().bind(scoreTable.widthProperty().multiply(.18));
        Player2Column.setStyle("-fx-alignment: CENTER;");
        Description.prefWidthProperty().bind(scoreTable.widthProperty().multiply(.40));

    }
    public void loadScores(ArrayList<Player> players) {

        Player2Column.setText(players.get(0).getName());
        player1Column.setText(players.get(1).getName());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<Score> player1Scores = new LinkedList<Score>();
                LinkedList<Score> player2Scores =new  LinkedList<Score>();

                LinkedList<Score> habitatScores1= HabitatScoring.getHabitatScore(players.get(0).getMap());
                LinkedList<Score> habitatScores2 = HabitatScoring.getHabitatScore(players.get(1).getMap());

                player1Scores.add(ElkScoring.elkScoringA(players.get(0).getMap()));
                player1Scores.add(SalmonScoring.salmonScoringA(players.get(0).getMap()));
                player1Scores.add(BearScoring.bearScoringA(players.get(0).getMap()));
                player1Scores.add(HawkScoring.hawkScoringA(players.get(0).getMap()));
                player1Scores.add(FoxScoring.foxScoringA(players.get(0).getMap()));

                player2Scores.add(ElkScoring.elkScoringA(players.get(1).getMap()));
                player2Scores.add(SalmonScoring.salmonScoringA(players.get(1).getMap()));
                player2Scores.add(BearScoring.bearScoringA(players.get(1).getMap()));
                player2Scores.add(HawkScoring.hawkScoringA(players.get(1).getMap()));
                player2Scores.add(FoxScoring.foxScoringA(players.get(1).getMap()));

                LinkedList<Row> rows = new LinkedList<Row>();
                int player1Total = 0;
                int player2Total = 0;

                for(int i=0;i<5;i++){
                    Row r = new Row();
                    Score s1 = player1Scores.get(i);
                    Score s2 = player2Scores.get(i);

                    r.setType(s1.name);
                    r.setPlayer1( s1.numberFound+" | " +s1.points);
                    r.setPlayer2( s2.numberFound+" | " +s2.points);
                    r.setDescription(s1.description);

                    player1Total+=s1.points;
                    player2Total+=s2.points;

                    rows.add(r);
                }


                for(int i=0;i<5;i++){
                    Row r = new Row();
                    Score s1 = habitatScores1.get(i);
                    Score s2 = habitatScores2.get(i);

                    r.setType(s1.name);
                    r.setPlayer1( s1.points+"");
                    r.setPlayer2( s2.points+"");
                    r.setDescription(s1.description);
                    player1Total+=s1.points;
                    player2Total+=s2.points;

                    Row bonus = new Row();
                    bonus.setType(s1.name + " Bonus");

                    if(s1.points>s2.points) {
                        bonus.setPlayer1("2");
                        bonus.setPlayer2("0");
                        player1Total+=2;
                    }
                    else if(s1.points<s2.points){
                        bonus.setPlayer1("0");
                        bonus.setPlayer2("2");
                        player2Total+=2;
                    }
                    else {
                        bonus.setPlayer1("0");
                        bonus.setPlayer2("0");
                    }
                    bonus.setDescription("Bonus points for largest habitat");

                    rows.add(r);
                    rows.add(bonus);
                }

                player1Total+=players.get(0).getNatureTokens();
                player2Total+=players.get(1).getNatureTokens();

                Row natureToken = new Row();
                natureToken.setType("Nature Tokens");
                natureToken.setPlayer1(players.get(0).getNatureTokens()+"");
                natureToken.setPlayer2(players.get(1).getNatureTokens()+"");
                natureToken.setDescription("Number of nature tokens");

                rows.add(natureToken);

                Row total = new Row();
                total.setType("Total");
                total.setPlayer1(player1Total+"");
                total.setPlayer2(player2Total+"");
                total.setDescription("Total score");

                rows.add(total);


                Platform.runLater(()->{
                    scoreTable.setItems(FXCollections.observableArrayList(rows));
                });
            }
        });

        t.start();
    }
}
