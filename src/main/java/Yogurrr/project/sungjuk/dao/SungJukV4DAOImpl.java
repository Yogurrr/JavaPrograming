package Yogurrr.project.sungjuk.dao;

import Yogurrr.project.sungjuk.model.SungJukVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SungJukV4DAOImpl implements SungJukV4DAO {

    private String insertSQL = "insert into sungjuk (name, kor, eng, mat, tot, avg, grd) values (?, ?, ?, ?, ?, ?, ?)";
    private String selectSQL = "select sjno, name, kor, eng, mat from sungjuk order by sjno";
    private String selectOneSQL = "select * from sungjuk where sjno = ?";
    private String updateSQL = "update sungjuk set name = ?, kor = ?, eng = ?, mat = ? where sjno = ?";
    private String deleteSQL = "delete from sungjuk where sjno = ?";

    @Override
    public int insertSungJuk(SungJukVO sj) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int cnt = 0;

        try {
            conn = MariaDB.makeConn();
            pstmt = conn.prepareStatement(insertSQL);

            pstmt.setString(1, sj.getNames());
            pstmt.setInt(2, sj.getKors());
            pstmt.setInt(3, sj.getEngs());
            pstmt.setInt(4, sj.getMats());
            pstmt.setInt(5, sj.getTots());
            pstmt.setDouble(6, sj.getAvgs());
            pstmt.setString(7, sj.getGrds()+"");

            cnt = pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("insertSungJuk에서 오류 발생!!");
            ex.printStackTrace();   // 예외의 자세한 내용 출력
        } finally {
            MariaDB.closeConn(null, pstmt, conn);
        }

        return cnt;
    }

    @Override
    public List<SungJukVO> selectSungJuk() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<SungJukVO> sjdata = new ArrayList<>();

        try {
            conn = MariaDB.makeConn();
            pstmt = conn.prepareStatement(selectSQL);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                SungJukVO sj = new SungJukVO(rs.getString(2),
                        rs.getInt(3), rs.getInt(4), rs.getInt(5));
                sj.setSjno(rs.getInt(1));
                sjdata.add(sj);
            }
        } catch (Exception ex) {
            System.out.println("selectSungJuk에서 오류 발생!!");
            ex.printStackTrace();
        } finally {
            MariaDB.closeConn(rs, pstmt, conn);
        }

        return sjdata;
    }

    @Override
    public SungJukVO selectOneSungJuk(int sjno) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = MariaDB.makeConn();
            pstmt = conn.prepareStatement(selectOneSQL);

        } catch (Exception ex) {
            System.out.println("selectSungJuk에서 오류 발생!!");
            ex.printStackTrace();
        } finally {
            MariaDB.closeConn(null, pstmt, conn);
        }

        return null;
    }

    @Override
    public int updateSungJuk(SungJukVO sj) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<SungJukVO> sjdata = new ArrayList<>();

        try {
            conn = MariaDB.makeConn();
            pstmt = conn.prepareStatement(updateSQL);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                SungJukVO sjs = new SungJukVO(rs.getString(1), rs.getInt(2), rs.getInt(3),
                        rs.getInt(4));
                sjdata.add(sjs);
            }
        } catch (Exception ex) {
            System.out.println("selectSungJuk에서 오류 발생!!");
            ex.printStackTrace();
        } finally {
            MariaDB.closeConn(rs, pstmt, conn);
        }

        return 0;
    }

    @Override
    public int deleteSungJuk(int sjno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<SungJukVO> sjdata = new ArrayList<>();

        try {
            conn = MariaDB.makeConn();
            pstmt = conn.prepareStatement(deleteSQL);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                SungJukVO sj = new SungJukVO(rs.getString(1), rs.getInt(2), rs.getInt(3),
                        rs.getInt(4));
                sjdata.add(sj);
            }
        } catch (Exception ex) {
            System.out.println("selectSungJuk에서 오류 발생!!");
            ex.printStackTrace();
        } finally {
            MariaDB.closeConn(rs, pstmt, conn);
        }

        return 0;
    }
}
