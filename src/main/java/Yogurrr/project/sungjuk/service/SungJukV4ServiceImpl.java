package Yogurrr.project.sungjuk.service;

import Yogurrr.project.sungjuk.dao.SungJukV4DAO;
import Yogurrr.project.sungjuk.dao.SungJukV4DAOImpl;
import Yogurrr.project.sungjuk.model.SungJukVO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SungJukV4ServiceImpl implements SungJukV1cService {   // 키보드 입력 받아서 리스트에 저장하는 역할
    private Scanner sc = null;
    private List<SungJukVO> sjs = null;
    private SungJukV4DAO sjdao = null;

    public SungJukV4ServiceImpl() {
        sc = new Scanner(System.in);
        sjdao = new SungJukV4DAOImpl();
    }

    // 성적 프로그램 메뉴
    public int displayMenu() {
        int menu = -1;

        StringBuffer sb = new StringBuffer();
        sb.append("---------------------------------\n")
                .append(" 성적 처리 프로그램 v4\n")
                .append("---------------------------------\n")
                .append("1. 성적 데이터 추가\n")
                .append("2. 성적 데이터 조회\n")
                .append("3. 성적 데이터 상세 조회\n")
                .append("4. 성적 데이터 수정\n")
                .append("5. 성적 데이터 삭제\n")
                .append("0. 프로그램 종료\n")
                .append("---------------------------------\n")
                .append("작업을 선택하세요");
        System.out.println(sb);

        try {
            menu = sc.nextInt();
        } catch (InputMismatchException ex) {
            // nextInt 시 문자를 입력했을때 입력 버퍼에 남은 문자찌꺼기 제거
            sc.nextLine();
            // 숫자가 아닌 그 이외 문자 입력시 menu에 -1 대입
            menu = -1;
        }

        return menu;
    }

    public void processMenu(int menu) {
        switch (menu) {
            case 1: newSungJuk(); break;
            case 2: readSungJuk(); break;
            case 3: readOneSungJuk(); break;
            case 4: modifySungJuk(); break;
            case 5: removeSungJuk(); break;
            case 0:
                // ArrayList에 저장된 성적데이터를 파일에 기록
//                sjdao.writeSungJuk(sjs);
                System.exit(0); break;
            default:
                System.out.println("\n>> 잘못 입력하셨습니다! <<\n");
        }
    }

    public void removeSungJuk() {
        // 번호 입력 -> 대상 검색 -> 대상 제거
        System.out.print("삭제할 학생번호는?");
        int sjno = sc.nextInt();

        if (sjdao.deleteSungJuk(sjno) > 0) System.out.println("성적데이터 삭제 완료!!");
    }

    public void modifySungJuk() {
        // 번호 입력 -> 대상 검색 -> 새로운 데이터 입력 -> 성적 처리
        SungJukVO sj = null;

        try {
            System.out.print("수정할 학생 번호는? ");
            int sjno = sc.nextInt();
            System.out.println("국어는? ");
            int kor = sc.nextInt();
            System.out.println("영어는? ");
            int eng = sc.nextInt();
            System.out.println("수학은? ");
            int mat = sc.nextInt();

            sj = new SungJukVO(null, kor, eng, mat);
            sj.setSjno(sjno);
        } catch (InputMismatchException ex) {
            System.out.println("\n잘못 입력하셨습니다... 다시 시도하세요!\n");
            sc.nextLine();
            return;
        }   // try - catch

        computeSungJuk(sj);
        if (sjdao.updateSungJuk(sj) > 0) System.out.println("\n성적 데이터 수정 완료!!\n");
    }

    public void readOneSungJuk() {
        // 번호 입력 -> 대상 검색 -> 대상 출력
        System.out.print("조회할 학생번호는? ");
        int sjno = sc.nextInt();

        SungJukVO sj = sjdao.selectOneSungJuk(sjno);

        if (sj != null) {
            System.out.printf("\n%s\n", sj);
        } else {
            System.out.println("찾는 데이터가 없습니다!\n");
        }
    }

    // 성적 리스트 조회 (이름, 국어, 영어, 수학)
    public void readSungJuk() {
        String fmt = "\n%d %s %d %d %d\n\n";

        try {
            for (SungJukVO sj : sjdao.selectSungJuk()) {
                if (sj != null) System.out.printf(fmt, sj.getSjno(), sj.getNames(), sj.getKors(), sj.getEngs(), sj.getMats());
            }   // sjs 배열에 저장된 모든 성적 데이터 출력
        } catch (NullPointerException ex) {
        }
    }

    // 성적 데이터 추가
    public void newSungJuk() {
        SungJukVO sj = null;

        try {
            sj = scanSungJuk();
            computeSungJuk(sj);
        } catch (InputMismatchException ime) {
            System.out.println("\n잘못 입력하셨습니다... 다시 시도하세요!\n");
            return;
        }

        int cnt = sjdao.insertSungJuk(sj);
        if (cnt > 0) System.out.println("성적 데이터 저장 완료!!");
    }

    // 성적 데이터 입력 받기
    private SungJukVO scanSungJuk() {
        String name = "";
        int kor = 0, eng = 0, mat = 0;

        try {
            System.out.print("이름은? ");
            name = sc.next();
            System.out.print("국어는? ");
            kor = sc.nextInt();
            System.out.print("영어은? ");
            eng = sc.nextInt();
            System.out.print("수학은? ");
            mat = sc.nextInt();
        } catch (InputMismatchException ex) {
            sc.nextLine();
            // scanSungJuk 메서드를 호출한 상위 메서드로 예외를 전파함
            throw new InputMismatchException();
        }

        return new SungJukVO(name, kor, eng, mat);
    }

    public void computeSungJuk(SungJukVO sj) {
        sj.setTots( sj.getKors() + sj.getEngs() + sj.getMats() );
        sj.setAvgs((double) sj.getTots() / 3);

        switch ((int)(sj.getAvgs() / 10)) {
            case 10:
            case 9: sj.setGrds('수'); break;
            case 8: sj.setGrds('우'); break;
            case 7: sj.setGrds('미'); break;
            case 6: sj.setGrds('양'); break;
            default: sj.setGrds('가');
        }
    }
}
