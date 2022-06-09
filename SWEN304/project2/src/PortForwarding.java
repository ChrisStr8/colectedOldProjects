import com.jcraft.jsch.*;
import java.awt.*;
import javax.swing.*;

public class PortForwarding{
	public static void main(String[] arg){

		int rport;
		String rhost;
		String lhost;
		int lport;

		try{
			JSch jsch=new JSch();

			String user = JOptionPane.showInputDialog("Enter username");
			String host = "depot.ecs.vuw.ac.nz"; //greta-pt.ecs.vuw.ac.nz  db.ecs.vuw.ac.nz  depot.ecs.vuw.ac.nz
			rport=5432;
			lhost="localhost";
			lport=8080;

			Session session=jsch.getSession(user, host, 22);

			// username and password will be given via UserInfo interface.
			UserInfo ui=new MyUserInfo();
			session.setUserInfo(ui);

			session.connect();

			// Channel channel=session.openChannel("shell");
			// channel.connect();

			session.setPortForwardingR(rport, lhost, lport);
			System.out.println(host+":"+rport+" -> "+lhost+":"+lport);

			//int assinged_port=session.setPortForwardingL(lport, host, rport);
			//System.out.println("localhost:"+assinged_port+" -> "+host+":"+rport);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{
		public String getPassword(){ return passwd; }
		public boolean promptYesNo(String str){
			Object[] options={ "yes", "no" };
			int foo=JOptionPane.showOptionDialog(null,
					str,
					"Warning",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			return foo==0;
		}

		String passwd;
		JTextField passwordField=(JTextField)new JPasswordField(20);

		public String getPassphrase(){ return null; }
		public boolean promptPassphrase(String message){ return true; }
		public boolean promptPassword(String message){
			Object[] ob={passwordField};
			int result=
					JOptionPane.showConfirmDialog(null, ob, message,
							JOptionPane.OK_CANCEL_OPTION);
			if(result==JOptionPane.OK_OPTION){
				passwd=passwordField.getText();
				return true;
			}
			else{ return false; }
		}
		public void showMessage(String message){
			JOptionPane.showMessageDialog(null, message);
		}
		final GridBagConstraints gbc =
				new GridBagConstraints(0,0,1,1,1,1,
						GridBagConstraints.NORTHWEST,
						GridBagConstraints.NONE,
						new Insets(0,0,0,0),0,0);
		private Container panel;
		public String[] promptKeyboardInteractive(String destination,
												  String name,
												  String instruction,
												  String[] prompt,
												  boolean[] echo){
			panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			gbc.weightx = 1.0;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridx = 0;
			panel.add(new JLabel(instruction), gbc);
			gbc.gridy++;

			gbc.gridwidth = GridBagConstraints.RELATIVE;

			JTextField[] texts=new JTextField[prompt.length];
			for(int i=0; i<prompt.length; i++){
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridx = 0;
				gbc.weightx = 1;
				panel.add(new JLabel(prompt[i]),gbc);

				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weighty = 1;
				if(echo[i]){
					texts[i]=new JTextField(20);
				}
				else{
					texts[i]=new JPasswordField(20);
				}
				panel.add(texts[i], gbc);
				gbc.gridy++;
			}

			if(JOptionPane.showConfirmDialog(null, panel,
					destination+": "+name,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE)
					==JOptionPane.OK_OPTION){
				String[] response=new String[prompt.length];
				for(int i=0; i<prompt.length; i++){
					response[i]=texts[i].getText();
				}
				return response;
			}
			else{
				return null;  // cancel
			}
		}
	}
}