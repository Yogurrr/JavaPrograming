package Yogurrr;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class J21Collection {
    // Collection
    // 데이터를 저장하는 자료구조와
    // 데이터를 처리(CRUD)하는 알고리즘을 체계적으로
    // 정리(구조화)해서 인터페이스와 클래스로 구현해 놓은 프레임워크

    public static void main(String[] args) {
        // ArrayList
        // 동적배열의 한 종류
        // List 인터페이스를 구현해서 만든 컬렉션 프레임워크
        // 요소의 저장순서가 유지
        // 중복으로 데이터를 저장할 수 있음
        // 크기가 늘어나면 새로운 배열을 생성하고
        // 기존의 요소들을 옮겨야 하는 복잡한 과정이 동반됨
        List<String> names = new ArrayList<>();
        // ArrayList names = new ArrayList();

        // 데이터 추가 : add(대상)
        names.add("혜교");
        names.add("지현");
        names.add("수지");

        // 특정 위치에 데이터 추가 : add(위치, 대상)
        names.add(1, "소희");

        // 조회 : enhanced for, forEach
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println("");

        // 특정 요소 조회 : get(위치)
        System.out.println(names.get(2));

        // 특정 요소 변경 : set(위치, 새로운값)
        names.set(3, "윤진");
        System.out.println(names.get(3));

        // 특정 요소 제거 : remove(위치)
//        names.remove(3);
//        System.out.println(names.get(3));

        // 특정 요소 제거 : remove(값)
//        names.remove("수지");
//        System.out.println(names.get(names.size() - 1));

        // 데이터 검색
        // 위치로 찾음 : get(위치), indexOf
        // 값으로 찾음 : forEach, contains

        // 지현을 검색 v1
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals("지현")) System.out.println((i + 1) + "번째에서 찾음");
        }   // 위치 기반 검색

        // 지현을 검색 v2
        for (String name : names) {
            if(name.equals("지현")) System.out.println("데이터 찾음");
        }   // 값 기반 검색

        // 지현을 검색 v3
        if (names.contains("지현")) System.out.println("데이터 찾음");   // 값 기반 검색

        // 지현을 검색 v4
        if (names.indexOf("지현") > -1) System.out.println("데이터 찾음");   // 위치 기반 검색

        // 게임 정보 동적 배열 생성
        List<GameInfo> games = new ArrayList<>();
        games.add(new GameInfo("디아블로4", 89600));
        games.add(new GameInfo("젤다의 전설", 74800));
        games.add(new GameInfo("바이오하자드 4 RE", 67000));

        // 조회
        // GameInfo 타입은 객체형이므로
        // 객체 자체 출력 시 메모리 주소가 출력됨
        // 따라서, toString을 재정의해 두어야 올바른 값이 출력
        for (GameInfo game : games) {
            System.out.println(game);
        }

        // 게임 '젤다의 전설'의 가격은?
        System.out.println("\n젤다의 전설의 가격은?");

        // v1 - for
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).name.equals("젤다의 전설")) {
                System.out.println(games.get(i).price);
            }
        }

        // v2 - forEach
        for (GameInfo game : games) {
            if (game.name.equals("젤다의 전설")) {
                System.out.println(game.price);
            }
        }

        // v3 - contains
        for (GameInfo game : games) {
            if (game.name.contains("젤다의 전설")) System.out.println(game.price);
        }

        // v4 - indexOf
        for (GameInfo game : games) {
            if (game.name.indexOf("젤다의 전설") > -1) System.out.println(game.price);
        }
    }
}

class GameInfo {
    String name;
    int price;

    public GameInfo(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        String fmt = "%s / %,d";
        return String.format(fmt, name, price);
    }
}