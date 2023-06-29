/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionCore;

import Interfaces.IEmailEventListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import utils.Command;
import utils.Email;
import utils.Extractor;

/**
 *
 * @author Elian
 */
public class MailVerificationThread implements Runnable{
    
    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;
    
    private IEmailEventListener emailEventListener;

    public IEmailEventListener getEmailEventListener() {
        return emailEventListener;
    }

    public void setEmailEventListener(IEmailEventListener emailEventListener) {
        this.emailEventListener = emailEventListener;
    }
    
    public MailVerificationThread(){
        socket=null;
        input =null;
        output=null;        
    }

    @Override
    public void run() {
        while(true){
            
        }
    }
    
    private void authUser(String email, String password) throws IOException{
        if(socket!=null && input!=null && output!=null){
            input.readLine();
            output.writeBytes(Command.user(email));//"USER ronaldorivero3@gmail.com \r\n");
            input.readLine();
            output.writeBytes(Command.pass(password));
            String message= input.readLine();
            if(message.contains("-ERR")){
                throw new AuthenticationException();
            }
        }
    }
    
    private void deleteEmails(int emails) throws IOException{
        for (int i = 1; i <= emails; i++) {
            output.writeBytes(Command.dele(i));
        }
    }
    
    private int getEmailCount(String line){
        String[] data = line.split(" ");
        return Integer.parseInt(data[1]);
    }
    
    private List<Email> getEmails(int count) throws IOException{
        List<Email> emails = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            output.writeBytes(Command.retr(i));
            String text= readMultiline();
            emails.add(Extractor.getEmail(text));
        }        
        return emails;
    }
    
    private String readMultiline() throws IOException{
        String lines = "";
        while (true) {            
            String line= input.readLine();
            if (line == null) {
                throw new IOException("Server no Responde (ocurrio un error al abrir el correo)");
            }
            if(line.equals(".")){
                break;
            }
            lines=lines + "\n" + line;
        }
        return lines;
    }
}
