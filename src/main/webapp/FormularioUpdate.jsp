<%@page import="com.orizon.formacion.java.DemoJavaEE7.entity.Producto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Lista de Productos</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>

<body>
	<h3 class="text-center p-2">Formulario de productos</h3>

	<div class="container">

		<!-- FORMULARIO -->
		<form name="formularioProductos" method="post" action="productos">
			<input type="hidden" name="action" value="update"> <input
				type="hidden" name="idProducto" value="${Producto.idproductos}">
			<div class="form-group">
				<div class="form-group">
					<label>Código</label> <input type="text" class="form-control"
						name="codigo" value="${Producto.codigo}">
				</div>
				<div class="form-group">
					<label>Sección</label> <input type="text" class="form-control"
						name="seccion" value="${Producto.seccion}">
				</div>
				<div class="form-group">
					<label>Nombre Artículo</label> <input type="text"
						class="form-control" name="nombreArticulo"
						value="${Producto.nombreArticulo}">
				</div>
				<div class="form-group">
					<label>Precio</label> <input type="text" class="form-control"
						name="precio" value="${Producto.precio}">
				</div>
				<button type="submit" class="btn btn-primary">Add</button>
			</div>
		</form>

	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>
</html>