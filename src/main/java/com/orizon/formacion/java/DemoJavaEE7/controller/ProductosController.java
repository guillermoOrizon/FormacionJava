package com.orizon.formacion.java.DemoJavaEE7.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.orizon.formacion.java.DemoJavaEE7.entity.Producto;
import com.orizon.formacion.java.DemoJavaEE7.model.ProductosDAO;

/**
 * Servlet implementation class ProductosController
 */
@WebServlet("/productos")
public class ProductosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/productos")
	private DataSource source;

	private List<Producto> productos;
	private Producto producto;
	private ProductosDAO productosDao;

	@Override
	public void init() throws ServletException {
		super.init();

		productosDao = new ProductosDAO(source);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("action");
		if (accion == null)
			accion = "list";

		switch (accion) {
		case "list":
			getProducts(request, response);
			break;
		case "load":
			loadProductById(request, response);
			break;
		case "delete":
			delete(request, response);
			break;
		case "insert":
			loadInsertForm(request, response);
			break;

		default:
			getProducts(request, response);
		}
	}

	private void loadInsertForm(HttpServletRequest request, HttpServletResponse response) {

		try {
			RequestDispatcher dtr = request.getRequestDispatcher("/FormularioInsert.jsp");
			dtr.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {

		String productId = request.getParameter("idProducto");

		try {
			productosDao.delete(productId);
			productos.removeIf(prod -> prod.getIdproductos() == Integer.parseInt(productId));

			request.setAttribute("listaProductos", productos);
			RequestDispatcher dtr = request.getRequestDispatcher("/VistaProductos.jsp");
			dtr.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadProductById(HttpServletRequest request, HttpServletResponse response) {

		String productId = request.getParameter("idProducto");

		try {

			producto = productosDao.getProductById(productId);

			request.setAttribute("Producto", producto);
			RequestDispatcher dtr = request.getRequestDispatcher("/FormularioUpdate.jsp");
			dtr.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	private void getProducts(HttpServletRequest request, HttpServletResponse response) {


		try {

			// [3] Recuperamos los productos desde la base de datos
			productos = productosDao.getAll();

			// [4] Adjuntamos nuestro modelo al objeto request
			request.setAttribute("listaProductos", productos);

			// [5] Comunicamos con el archivo JSP
			RequestDispatcher dtr = request.getRequestDispatcher("/VistaProductos.jsp");

			// [6] Enviamos la información al archivo JSP
			dtr.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("action");
		// if (accion == null) accion = "insert";

		switch (accion) {
		case "insert":
			insertProduct(request, response);
			break;
		case "update":
			updateProduct(request, response);
			break;

		default:
		}

	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response) {

		Integer productId = Integer.parseInt(request.getParameter("idProducto"));

		String codigo = request.getParameter("codigo");
		String seccion = request.getParameter("seccion");
		String nombreArticulo = request.getParameter("nombreArticulo");
		double precio = Double.parseDouble(request.getParameter("precio"));
		Date fecha = new Date(System.currentTimeMillis());

		Producto producto = new Producto(productId, codigo, seccion, nombreArticulo, precio, fecha);

		try {

			productosDao.update(producto);
			productos.removeIf(prod -> prod.getIdproductos() == productId);
			productos.add(producto);

			request.setAttribute("listaProductos", productos);
			RequestDispatcher dtr = request.getRequestDispatcher("/VistaProductos.jsp");
			dtr.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private void insertProduct(HttpServletRequest request, HttpServletResponse response) {

		String codigo = request.getParameter("codigo");
		String seccion = request.getParameter("seccion");
		String nombreArticulo = request.getParameter("nombreArticulo");
		double precio = Double.parseDouble(request.getParameter("precio"));
		Date fecha = new Date(System.currentTimeMillis());

		Producto producto = new Producto(codigo, seccion, nombreArticulo, precio, fecha);

		try {

			productosDao.insert(producto);
			productos = productosDao.getAll();
			request.setAttribute("listaProductos", productos);
			RequestDispatcher dtr = request.getRequestDispatcher("/VistaProductos.jsp");
			dtr.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
