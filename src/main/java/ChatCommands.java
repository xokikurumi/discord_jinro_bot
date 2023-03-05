import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ChatCommands {

	public static void sendDice(MessageReceivedEvent e) {
		String msg = e.getMessage().getContentRaw();
		
		String[] dice = msg.split("[dD]");
		if(Integer.parseInt(dice[0]) != 1) {
			String result = "[ ";
			int resultInt = 0;
			for(int r = 0; r < Integer.parseInt(dice[0]); r++) {
				int random = 0;
				try {
					SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
					random = sr.nextInt(Integer.parseInt(dice[1])) + 1;
				} catch (NoSuchAlgorithmException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
				resultInt += random;
				result += random + " , ";
			}
			
			e.getMessage().getChannel().sendMessage(msg + " > " + (result.subSequence(0, result.length()-2)) + " ] " + resultInt).queue();
		}else {
			int random = 0;
			try {
				SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
				random = sr.nextInt(Integer.parseInt(dice[1])) + 1;
			} catch (NoSuchAlgorithmException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			
			
			e.getMessage().getChannel().sendMessage(msg + " > " + random).queue();
		}
	}
	
	
	
	public static void sendNano(MessageReceivedEvent e) {
		String msg = e.getMessage().getContentRaw();
		e.getMessage().addReaction(Emoji.fromUnicode("🎣")).queue();
	}
}
