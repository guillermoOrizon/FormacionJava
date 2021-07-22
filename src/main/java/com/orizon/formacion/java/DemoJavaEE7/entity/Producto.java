package com.orizon.formacion.java.DemoJavaEE7.entity;

import java.util.Date;

public class Producto {

	private Integer idproductos;
	private String codigo;
	private String seccion;
	private String nombreArticulo;
	private double precio;
	private Date fecha;

	public Producto(String codigo, String seccion, String nombreArticulo, double precio, Date fecha) {

		this.codigo = codigo;
		this.seccion = seccion;
		this.nombreArticulo = nombreArticulo;
		this.precio = precio;
		this.fecha = fecha;
	}

	public Producto(Integer idproductos, String codigo, String seccion, String nombreArticulo, double precio,
			Date fecha) {

		this.idproductos = idproductos;
		this.codigo = codigo;
		this.seccion = seccion;
		this.nombreArticulo = nombreArticulo;
		this.precio = precio;
		this.fecha = fecha;
	}

	public Integer getIdproductos() {
		return idproductos;
	}

	public void setIdproductos(Integer idproductos) {
		this.idproductos = idproductos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Productos [codigo=" + codigo + ", seccion=" + seccion + ", nombreArticulo=" + nombreArticulo
				+ ", precio=" + precio + ", fecha=" + fecha + "]";
	}

}
