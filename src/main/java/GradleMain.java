import java.util.Random;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class GradleMain extends ListenerAdapter implements EventListener{
	
	private static JDA jda;
	private static String TOKEN = "TOKEN";
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Discord BOT.");
		
		jda = JDABuilder.createDefault(TOKEN)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT)
				.enableIntents(GatewayIntent.GUILD_VOICE_STATES)
				.setStatus(OnlineStatus.ONLINE)
				.setActivity(Activity.playing("ずんだもんなのだ"))
				.build();
		jda.addEventListener(new GradleMain());
		//		jda.awaitReady();
	}

	@Override
	public void handleEvent(Event evt) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("EVENT.");
	}
	
	//1081772688223260702
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		String msg = e.getMessage().getContentRaw();
		User user = e.getMember().getUser();
		if(e.getAuthor().isBot()) {
			return;
		}

		if(msg.matches("^[0-9]+[d][0-9]+$")) {
			String[] dice = msg.split("[dD]");
			if(Integer.parseInt(dice[0]) != 1) {
				String result = "[";
				int resultInt = 0;
				for(int r = 0; r < Integer.parseInt(dice[0]); r++) {
					int random = new Random().nextInt(Integer.parseInt(dice[1]) - 1) + 1;
					System.out.println("dice: " + random);
					resultInt += random;
					result += random + " ,";
				}
				
				e.getMessage().getChannel().sendMessage(msg + " > " + (result.subSequence(0, result.length()-1)) + "] " + resultInt).queue();
			}else {
				int random = new Random().nextInt(Integer.parseInt(dice[1]) - 1) + 1;
				
				e.getMessage().getChannel().sendMessage(msg + " > " + random).queue();
			}
			
		}
		
		
		if(msg.equalsIgnoreCase("j_join")) {

			
			if(e.getGuild().getMemberById(user.getId()).getVoiceState().inAudioChannel()) {
				String vcId = e.getGuild().getMemberById(user.getId()).getVoiceState().getChannel().getId();
				System.out.println("Audio Channel id: " + vcId);
				
				
				AudioChannelUnion connectedChannel = e.getMember().getVoiceState().getChannel();
				AudioManager am = e.getGuild().getAudioManager();
				am.openAudioConnection(connectedChannel);

				e.getMessage().getChannel().sendMessage("Botがボイスチャットに参加します.").queue();
			}
//			if(jda.getVoiceChannelById(e.getChannel().asVoiceChannel().))
			
		}
		if(msg.equalsIgnoreCase("j_leave")) {
			
			if(e.getGuild().getMemberById(user.getId()).getVoiceState().inAudioChannel()) {
				String vcId = e.getGuild().getMemberById(user.getId()).getVoiceState().getChannel().getId();
				System.out.println("Audio Channel id: " + vcId);

				e.getGuild().getAudioManager().closeAudioConnection();
				
				
				e.getMessage().getChannel().sendMessage("Botがボイスチャットから離れました.").queue();
			}
//			if(jda.getVoiceChannelById(e.getChannel().asVoiceChannel().))
			
		}
	}
}
