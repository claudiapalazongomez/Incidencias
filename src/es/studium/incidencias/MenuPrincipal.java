package es.studium.incidencias;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

public class MenuPrincipal implements ActionListener, WindowListener
{
	Frame menuPrincipal = new Frame("Menú Principal");
	MenuBar barraMenu = new MenuBar();
	Menu menuUsuario = new Menu("Usuarios");
	Menu menuElementos = new Menu("Elementos");
	Menu menuIncidencias = new Menu("Incidencias");
	MenuItem mniUsuarioNuevo = new MenuItem("Nuevo");
	MenuItem mniUsuarioListado = new MenuItem("Listado");
	MenuItem mniUsuarioBaja = new MenuItem("Baja");
	MenuItem mniUsuarioModificar = new MenuItem("Modificar");
	MenuItem mniElementoNuevo = new MenuItem("Nuevo");
	MenuItem mniElementoListado = new MenuItem("Listado");
	MenuItem mniElementoBaja = new MenuItem("Baja");
	MenuItem mniElementoModificar = new MenuItem("Modificar");
	
	int tipoUsuario;
	
	MenuPrincipal(int t){
		tipoUsuario = t;
		menuPrincipal.setLayout(new FlowLayout());
		menuPrincipal.addWindowListener(this);
		menuPrincipal.setMenuBar(barraMenu);
		
		mniUsuarioNuevo.addActionListener(this);
		mniUsuarioListado.addActionListener(this);
		mniUsuarioBaja.addActionListener(this);
		mniUsuarioModificar.addActionListener(this);
		menuUsuario.add(mniUsuarioNuevo);
		if(tipoUsuario==0) {
		menuUsuario.add(mniUsuarioListado);
		menuUsuario.add(mniUsuarioBaja);
		menuUsuario.add(mniUsuarioModificar);
		}
		
		mniElementoNuevo.addActionListener(this);
		mniElementoListado.addActionListener(this);
		mniElementoBaja.addActionListener(this);
		mniElementoModificar.addActionListener(this);
		menuElementos.add(mniElementoNuevo);
		if(tipoUsuario==0) {
		menuElementos.add(mniElementoListado);
		menuElementos.add(mniElementoBaja);
		menuElementos.add(mniElementoModificar);
		}
		
		barraMenu.add(menuUsuario);
		barraMenu.add(menuElementos);
		barraMenu.add(menuIncidencias);
		
		
		menuPrincipal.setLocationRelativeTo(null);
		menuPrincipal.setResizable(false);	
		menuPrincipal.setSize(400,400);
		menuPrincipal.setVisible(true);
	}
	
	@Override
	public void windowOpened(WindowEvent e)
	{}
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);	
	}
	public void windowClosed(WindowEvent e)
	{}
	public void windowIconified(WindowEvent e)
	{}
	public void windowDeiconified(WindowEvent e)
	{}
	public void windowActivated(WindowEvent e)
	{}
	public void windowDeactivated(WindowEvent e)
	{}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//Nuevo usuario
		if(e.getSource().equals(mniUsuarioNuevo)) {
			new NuevoUsuario();
		}
		
		//Listado usuario
		else if(e.getSource().equals(mniUsuarioListado)) {
			new ListadoUsuarios();
		}
		
		//Baja usuario
		else if(e.getSource().equals(mniUsuarioBaja)) {
			new BajaUsuario();
		}
		
		//Modificar usuario
		else if(e.getSource().equals(mniUsuarioModificar))
		{
			new ModificarUsuario();
		}
		
	}
}
