package gameStateManager;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameStateLogin extends GameState {

	public GameStateLogin() {
		
	}

	@Override
	public void run() {
		  Stage palco = new Stage(); 
		
		  VBox raiz = new VBox(10); // 1
		  raiz.setAlignment(Pos.CENTER); // 2
		 
		  Label rotuloLogin = new Label("Login");
		  		 
		  TextField loginInput = new TextField();
		  loginInput.setPromptText("Type your login");
		 
		  Label rotuloSenha = new Label("Senha");
	  		 
		  PasswordField senhaInput = new PasswordField();
		  senhaInput.setPromptText("Type your password");
		 
		  Button button = new Button("Login");
		  
		  raiz.getChildren().addAll(rotuloLogin, loginInput, rotuloSenha, senhaInput,button);
		 
		  
		  Scene cena = new Scene(raiz, 300, 400);
		  palco.setTitle("Pokemon");
		  palco.setScene(cena);
		  palco.show();
	}

	

}
