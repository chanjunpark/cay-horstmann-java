package com.company;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

//        practiceInput();
//        practiceConsole(); -> 책에 있는대로 작성했는데 안 됨;;
//        practiceArrayList();
        practiceEnhancedLoop();

    }

    public static void practiceEnhancedLoop(){

        int[] numbers = {1,2,3,4,5,6,7,8,9,10};
        int sum = 0;
        for (int n : numbers){
            sum += n;
        }
        System.out.println(sum);

        String[] friends = {"James", "John", "Hide"};
        for (String name : friends) {
            System.out.println(name);
        }

    }

    public static void practiceArrayList(){

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(42);
        int first = numbers.get(0);
        System.out.print(first);

    }

    public static void practiceInput(){

        Scanner in = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = in.nextLine();
        System.out.println(name);

        String firstName = in.next();
        System.out.println(firstName);

        System.out.println("How old are you?");
        int age = in.nextInt();
        System.out.println((age));
    }

    public static void practiceConsole(){
        // Scanner는 입력이 터미널에 보이므로 비밀번호를 읽을 때는 Scanner 대신 Console Calss를 사용
        Console terminal = System.console();
        String username = terminal.readLine("User name: ");
        char[] passwd = terminal.readPassword("Password: ");

//        System.out.println(username);
//        for(int i=0;i<passwd.length;i++){
//            System.out.println(passwd[i]);
//        }
    }
}
