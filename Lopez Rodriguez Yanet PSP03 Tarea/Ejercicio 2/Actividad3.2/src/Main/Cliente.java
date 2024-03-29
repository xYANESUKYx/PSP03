package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

 /**@author yanet
 *Actividad 3.2. El objetivo del ejercicio es crear una aplicación cliente/servidor que permita el envío de ficheros al cliente. Para ello, 
 * el cliente se conectará al servidor por el puerto 1500 y le solicitará el nombre de un fichero del servidor. Si el fichero existe, el 
 * servidor, le enviará el fichero al cliente y éste lo mostrará por pantalla. Si el fichero no existe, el servidor le enviará al cliente 
 * un mensaje de error. Una vez que el cliente ha mostrado el fichero se finalizará la conexión.*/

public class Cliente {
    public static void main(String[] args){
        try {
            //CREAMOS UN NUEVO Socket Y LO CONECTAMOS AL SERVIDOR EL CUAL SE ENCUENTRA EN "localhost" EN EL PUERTO 1500
            Socket socket = new Socket("localhost", 1500);
            
            //CREAMOS UN NUEVO DataInputStream PARA LEER DATOS DEL Socket
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            //CREAMOS UN NUEVO DataOutputStream PARA ESCRIBIR DATOS DEL Socket
            DataOutputStream ouputStream = new DataOutputStream(socket.getOutputStream());
            
            //LE PEDIMOS AL CLIENTE LA RUTA DEL ARCHIVO EJ: C:\Users\yanet\Desktop\xd.txt
            System.out.println("Introduce la ruta del archivo: ");
            Scanner scn = new Scanner(System.in);
            //ESPECIFICAMOS EL DELIMITADOR Y GUARDAMOS LA RUTA EN SU VARIABLE
            scn.useDelimiter("\n");
            String ruta = scn.next();
            
            //ESCRIBIMOS LA RUTA EN EL FLUJO DE SALIDA
            ouputStream.writeUTF(ruta);
            
            //SI LA RUTA EXISTE
            if (inputStream.readBoolean()){
                //LEEMOS LA LONGITUD DEL CONTENIDO DEL ARCHIVO
                int longitud = inputStream.readInt();
                //CREAMOS LA VARIABLE contenido CON LA MISMA LONGITUD DEL CONTENIDO
                byte[] contenido = new byte[longitud];
                
                //RECORREMOS LA VARIABLE contenido ASIGNANDOLE A CADA BYTE EL VALOR LEIDO DE inputStream
                for (int i = 0; i < longitud; i++) {
                    contenido[i] = inputStream.readByte();
                }
                
                //LE MOSTRAMOS AL CLIENTE EL CONTENIDO DEL ARCHIVO
                System.out.println(new String(contenido));
            
            //SI LA RUTA DEL ARCHIVO NO ES CORRECTA MOSTRAMOS "El archivo no existe" AL CLIENTE
            }else{
                System.out.println("El archivo no existe.");
            }
            
            //CERRAMOS LA CONEXIÓN
            socket.close();
            
        //SI SE PRODUCE UNA EXCEPCIÓN EL catch LO CAPTURA Y MUESTRA EL ERROR Y EL stack trace   
        }catch(IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


