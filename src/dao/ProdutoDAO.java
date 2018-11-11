package dao;

import connection.ConnectionFactory;
import model.ProdutoModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO {

    Connection con = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;

    public void createProduto(ProdutoModel pm){

        try {
            stmt = con.prepareStatement("insert into produto (nome, categoria, valor, estoque, descricao) values (?, ?, ?, ?, ?)");
            stmt.setString(1, pm.getNomeProd().toUpperCase());
            stmt.setString(2, pm.getCategoria().toUpperCase());
            stmt.setDouble(3, pm.getvUnitProd());
            stmt.setInt(4, pm.getEstoque());
            stmt.setString(5, pm.getDescProd().toUpperCase());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateProduto(ProdutoModel pm){

        try {
            stmt = con.prepareStatement("update produto set nome=?, categoria=?, valor=?, estoque=?, descricao=? where codigo=?");
            stmt.setString(1, pm.getNomeProd().toUpperCase());
            stmt.setString(2, pm.getCategoria().toUpperCase());
            stmt.setDouble(3, pm.getvUnitProd());
            stmt.setInt(4, pm.getEstoque());
            stmt.setString(5, pm.getDescProd().toUpperCase());
            stmt.setInt(6,pm.getCodProd());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void deleteProduto(ProdutoModel pm){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("delete from produto where nome=?");
            stmt.setString(1, pm.getNomeProd());
            stmt.execute();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        }finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public ArrayList<ProdutoModel> readAllProdutos(){

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<ProdutoModel> pmArray =  new ArrayList<>();
        ProdutoModel pmObjeto;

        try{
            stmt = con.prepareStatement("select * from produto");
            rs = stmt.executeQuery();

            while(rs.next()){
                pmObjeto = new ProdutoModel();
                pmObjeto.setCodProd(rs.getInt("codigo"));
                pmObjeto.setNomeProd(rs.getString("nome"));
                pmObjeto.setCategoria(rs.getString("categoria"));
                pmObjeto.setvUnitProd(rs.getDouble("valor"));
                pmObjeto.setEstoque(rs.getInt("estoque"));
                pmObjeto.setDescProd(rs.getString("descricao"));
                pmArray.add(pmObjeto);
            }

        }catch(SQLException ex){
            System.out.println("Erro ao buscar todos alunos: "+ex.getMessage());
            return null;
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return pmArray;
    }


}
