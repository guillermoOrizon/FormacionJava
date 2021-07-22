package com.orizon.formacion.java.DemoJavaEE7.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.orizon.formacion.java.DemoJavaEE7.entity.Producto;

public class ProductosDAO {

	private Producto producto;
	private DataSource source;

	public ProductosDAO(DataSource source) {
		this.source = source;
	}

	public List<Producto> getAll() throws Exception {

		List<Producto> productos = new ArrayList<>();

		Connection con = null;
		Statement smt = null;
		ResultSet rs = null;

		try {

			con = source.getConnection();
			String sql = "SELECT * FROM db.productos";
			smt = con.createStatement();
			rs = smt.executeQuery(sql);

			while (rs.next()) {

				Integer id = rs.getInt("idproductos");
				String codigo = rs.getString("codigo");
				String seccion = rs.getString("seccion");
				String nombreArticulo = rs.getString("nombre_articulo");
				Double precio = rs.getDouble("precio");
				Date fecha = rs.getDate("fecha");

				producto = new Producto(id, codigo, seccion, nombreArticulo, precio, fecha);
				productos.add(producto);
			}

			rs.close();
			smt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productos;

	}

	public void insert(Producto producto) {
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = source.getConnection();
			String sql = "INSERT INTO db.productos (`codigo`, `seccion`,`nombre_articulo`,`precio`,`fecha`) VALUES (?,?,?,?,?);";
			ps = con.prepareStatement(sql);
			ps.setString(1, producto.getCodigo());
			ps.setString(2, producto.getSeccion());
			ps.setString(3, producto.getNombreArticulo());
			ps.setDouble(4, producto.getPrecio());

			java.sql.Date fechaSQL = new java.sql.Date(producto.getFecha().getTime());
			ps.setDate(5, fechaSQL);

			ps.execute();

			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Producto getProductById(String id) {

		Producto producto = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = source.getConnection();
			String sql = "SELECT * FROM db.productos WHERE idproductos=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {

				Integer idproductos = rs.getInt("idproductos");
				String codigo = rs.getString("codigo");
				String seccion = rs.getString("seccion");
				String nombreArticulo = rs.getString("nombre_articulo");
				Double precio = rs.getDouble("precio");
				Date fecha = rs.getDate("fecha");

				producto = new Producto(idproductos, codigo, seccion, nombreArticulo, precio, fecha);
			} else {
				throw new Exception("No se ha encotrado el producto con id: " + id);
			}

			ps.close();
			rs.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return producto;
	}

	public void update(Producto producto) {
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = source.getConnection();
			String sql = "UPDATE db.productos SET codigo=?,seccion=?,nombre_articulo=?,precio=?,fecha=? WHERE idproductos=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, producto.getCodigo());
			ps.setString(2, producto.getSeccion());
			ps.setString(3, producto.getNombreArticulo());
			ps.setDouble(4, producto.getPrecio());

			java.sql.Date fechaSQL = new java.sql.Date(producto.getFecha().getTime());
			ps.setDate(5, fechaSQL);

			ps.setDouble(6, producto.getIdproductos());

			ps.execute();

			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(String id) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = source.getConnection();
			String sql = "DELETE FROM db.productos WHERE idproductos=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.execute();

			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
