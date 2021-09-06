/* Este Proyecto fue logrado gracias a APIs & Paqutes hechos por Programadores Expertos, sin embargo.
El Credito se lo lleva Netting por haber hecho funcionar de tal modo tales Paquetes & APIs.
© Prod Ryan 2021 - Coded by Prod Ryan [ Netting ] */

// ----- [ Importacion de APIs ] ----- //
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class netting {

    // ----- [ Variables ] ----- //
    public static void Banner() {
        System.out.println("\n" + "    [ " + ANSI_CYAN + "Netting Tool" + ANSI_RESET + " ]");
        System.out.println("\n" + "    [ " + ANSI_CYAN + "Dev: Netting" + ANSI_RESET + " ]");
    }

    public static void CLS() throws IOException, InterruptedException {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    // ----- [ Menu ] ----- //
    public static void main(final String... args) throws InterruptedException, ExecutionException, IOException, InterruptedException {
        CLS();
        Banner();

        System.out.println("\n" + "    1 - Port Scanner\n" + "    2 - Encriptar con Hash\n" + "\n" + "    4 - Salir");
        System.out.println("\n" + "    [ - ] > ");
        Scanner scan = new Scanner(System.in);
        final int opciones = scan.nextInt();

        if (opciones == 1) {
            PortScanner();
        } else if (opciones == 2) {
            Hashes();
        } else if (opciones == 4) {

        } else {
            CLS();
            main();
        }
    }

    // ----- [ Funciones ] ----- //
    public static void PortScanner() throws InterruptedException, ExecutionException {
        final ExecutorService es = Executors.newCachedThreadPool();
        System.out.println("\n" + "    [ Ingrese la Direccion IP del host que quiere Escanear ]");
        System.out.print("\n" + "    [ - ] > ");
        Scanner scan = new Scanner(System.in);
        final String ip = scan.nextLine();
        final int timeout = 100;
        final List<Future<Resultado>> futures = new ArrayList<>();
        for (int port = 1; port <= 65535; port++) {
            futures.add(portIsOpen(es, ip, port, timeout));
        }
        es.awaitTermination(100L, TimeUnit.MILLISECONDS);
        int openPorts = 0;
        for (final Future<Resultado> f : futures) {
            if (f.get().isOpen()) {
                openPorts++;
                System.out.println("\n" + "    [ + ] " + ip + ":" + f.get().getPort());
            }
        }
        System.out.println("\n" + "    [ + ] Un total de " + openPorts + " puertos abiertos en " + ip + " (en un tiempo de " + timeout + "ms)" + "\n");
        es.shutdown();
    }
    
    public static Future<Resultado> portIsOpen(final ExecutorService es, final String ip, final int port,
    final int timeout) {
        return es.submit(new Callable<Resultado>() {
            @Override
            public Resultado call() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    return new Resultado(port, true);
                } catch (Exception ex) {
                    return new Resultado(port, false);
                }
            }
        });
    }  

    public static void Hashes() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n" + "    1 - MD5\n" + "    2 - MD2\n" + "    3 - SHA256" + "\n" + "    4 - SHA512");
        System.out.println("\n" + "    [ - ] > ");
        int hash = scan.nextInt();

        if (hash == 1) {
            md5();
        } else if (hash == 2) {
            md2();
        } else if (hash == 3) {
            sha256();
        } else if (hash == 4) {
            sha512();
        } else {
            Hashes();
        }
    }

    // ----- [ SubFunciones ] ----- //
    public static class Resultado {
        private int port;
        private boolean isOpen;
        
        public Resultado(int port, boolean isOpen) {
            super();
            this.port = port;
            this.isOpen = isOpen;
        }
        
        public int getPort() {
            return port;
        }
        
        public void setPort(int port) {
            this.port = port;
        }
        
        public boolean isOpen() {
            return isOpen;
        }
        
        public void setOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }
    }

    public static void md5() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("\n" + "    [ Ingrese el dato a Encriptar en MD5 ]");
            System.out.println("\n" + "    [ - ] > ");
            String md5 = scan.next();

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(md5.getBytes());
            byte[] digest = md.digest();
            StringBuffer e = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                e.append(Integer.toHexString(0xFF & digest[i]));
            }
            System.out.println("\n" + "    [ MD5 ] > " + e.toString());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("\n" + "    [ No he podido Encriptar ] ");
        }
    }

    public static void md2() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("\n" + "    [ Ingrese el dato a Encriptar en MD2 ]");
            System.out.println("\n" + "    [ - ] > ");
            String md2 = scan.next();

            MessageDigest md = MessageDigest.getInstance("MD2");
            md.update(md2.getBytes());
            byte[] digest = md.digest();
            StringBuffer e = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                e.append(Integer.toHexString(0xFF & digest[i]));
            }
            System.out.println("\n" + "    [ MD2 ] > " + e.toString());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("\n" + "    [ No he podido Encriptar ] ");
        }
    }

    public static void sha256() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("\n" + "    [ Ingrese el dato a Encriptar en SHA256 ]");
            System.out.println("\n" + "    [ - ] > ");
            String sha256 = scan.next();

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(sha256.getBytes());
            byte[] digest = md.digest();
            StringBuffer e = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                e.append(Integer.toHexString(0xFF & digest[i]));
            }
            System.out.println("\n" + "    [ SHA256 ] > " + e.toString());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("\n" + "    [ No he podido Encriptar ] ");
        }
    }    
    
    public static void sha512() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("\n" + "    [ Ingrese el dato a Encriptar en SHA512 ]");
            System.out.println("\n" + "    [ - ] > ");
            String sha512 = scan.next();

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(sha512.getBytes());
            byte[] digest = md.digest();
            StringBuffer e = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                e.append(Integer.toHexString(0xFF & digest[i]));
            }
            System.out.println("\n" + "    [ SHA512 ] > " + e.toString());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("\n" + "    [ No he podido Encriptar ] ");
        }
    }
}