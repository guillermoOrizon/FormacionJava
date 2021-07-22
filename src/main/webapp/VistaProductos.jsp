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
	<h3 class="text-center p-2">Listado de productos</h3>

	<div class="container">

		<c:url var="link_insert" value="productos">
			<c:param name="action" value="insert"></c:param>
		</c:url>
		<a class="btn btn-primary" href="${link_insert}">Add</a>
		<br>
		<br>
		
		<!-- TABLA -->
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">Codigo</th>
					<th scope="col">Seccion</th>
					<th scope="col">Nombre Articulo</th>
					<th scope="col">Precio</th>
					<th scope="col">Acciones</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="tempProductos" items="${listaProductos}">

					<c:url var="link_update" value="productos">
						<c:param name="action" value="load"></c:param>
						<c:param name="idProducto" value="${tempProductos.idproductos}"></c:param>
					</c:url>

					<c:url var="link_delete" value="productos">
						<c:param name="action" value="delete"></c:param>
						<c:param name="idProducto" value="${tempProductos.idproductos}"></c:param>
					</c:url>

					<tr>
						<td>${tempProductos.codigo}</td>
						<td>${tempProductos.seccion}</td>
						<td>${tempProductos.nombreArticulo}</td>
						<td>${tempProductos.precio}</td>
						<td><a class="btn btn-warning" href="${link_update}">Actualizar</a>
							<a class="btn btn-danger" href="${link_delete}">Borrar</a></td>
					</tr>

				</c:forEach>

			</tbody>
		</table>

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