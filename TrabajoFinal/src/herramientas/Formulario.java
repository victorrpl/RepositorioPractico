package herramientas;
import java.sql.*;
import datos.Conexion;
import java.awt.*;                             /*HECHO POR VICTOR RAFAEL (18-1018)*/
import java.util.*;                     /*Derechos reservados: copyrigth, victor.com 2020, XD*/
import javax.swing.*;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dialog.ModalExclusionType;
//se importan todas las librerias a utilizar (el compilador lo va haciendo con los eventos y demas).

public class Formulario extends JFrame {
//se declaran los diferentes componentes utilizados mas adelante
	private JPanel contentPane;
	private JTextField t_codigo;
	private JTextField t_nombre;
	private JTextField t_edad;
	private JTextField t_apellido;
	private JTextField t_direccion;
	

	/**
	 * Launch the application.
	 */
    //METODO PRINCIPAL DE LA CLASE, ENCARGADA DE CORRER EL PROGRAMA.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
				try {
					Formulario frame = new Formulario();
					frame.setVisible(true);//hace visible la aplicacion o JFrame.
					frame.setLocationRelativeTo(null);//AL ABRIR EL PROGRAMA, SE CENTRA EN MEDIO DE LA PANTALLA EL FORMULARIO
					
				} catch (Exception e) {
					e.printStackTrace();//en caso de excepcion se imprime la pila de errores
				}
			}
		});
	}

	
	// CONSTRUCTOR PRINCIPAL DEL FORMULARIO
	public Formulario() {
		setUndecorated(true);//Esta funcion elimina el boton de cerrar ventana que viene por default en el JFrame.
		setForeground(Color.WHITE);//color de letras
		
		this.setLocation(400,250);
	    setLocationRelativeTo(null);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setForeground(Color.BLACK);
		getContentPane().setLayout(new CardLayout(0, 0));
     
		//SE ESTABLECE UNA IMAGEN COMO ICONO DE LA APLICACION.
		setBackground(Color.BLACK);                       //lugar donde se encuentra la imagen 
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\victor\\Desktop\\fd.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 497);
		//se crea el contenedor del formulario o JPanel
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//SE CREA UN COMBOBOX y se declaran todos sus valores o propiedades.
	    JComboBox combo_genero = new JComboBox();
		combo_genero.setFont(new Font("Tahoma", Font.BOLD, 12));
		combo_genero.setBackground(Color.LIGHT_GRAY);//color de fondo
		combo_genero.setForeground(Color.BLACK);//color de letras
		combo_genero.addItem("Masculino");//Valor por default 
		combo_genero.addItem("Femenino");//Valor por default que contendra este jcombo_box
		combo_genero.setEnabled(false);//por default este combo_box, esta desabilitado.
		combo_genero.setBounds(113, 247, 103, 22);
		contentPane.add(combo_genero);
		
		
		
		//Creacion del boton guardar
		JButton btnGuardar = new JButton("");
		//CREAMOS EL BOTON NUEVO Y LE ASIGNAMOS UN EVENTO.
				JButton btn_Nuevo = new JButton("");
				btn_Nuevo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btn_Nuevo.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						//SE HABILITAN LAS CAJAS DE TEXTO Y EL BOTON DE GUARDAR.
						t_nombre.setEnabled(true);
						t_codigo.setEnabled(true);
						t_apellido.setEnabled(true);
						t_direccion.setEnabled(true);
						t_edad.setEnabled(true);
						combo_genero.setEnabled(true);
						btnGuardar.setEnabled(true);
						
						//El boton Nuevo una vez pulsado se desabilita.
						btn_Nuevo.setEnabled(false);	
					}
				});
		
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//FUNCION QUE DETERMINA QUE TIPO DE CURSOR DE USARA EN CASO DE PASAR EL MOUSE POR EL BOTON.
		btnGuardar.setEnabled(false);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hacemos una instancia de la clase Conexion para usar sus metodos.
				Conexion cc = new Conexion();
				
				//declaramos variables temporales para almacenar los valores de los diferentes TextField.
				String nom="",ape="",direc="",gen;
				String edad,cod;
				
				//se almacena el valor de las diferentes cajas de texto a variables locales.
				cod=t_codigo.getText().toString();
				nom = t_nombre.getText();
				ape = t_apellido.getText().toString();
				direc = t_direccion.getText().toString();
				edad  = t_edad.getText().toString();
				gen = combo_genero.getSelectedItem().toString();
				String sql ="INSERT INTO participante(Codigo,Nombre,Apellido,Genero,Edad,Direccion) VALUES(?,?,?,?,?,?)";//SENTENCIA SQL, QUE SE VA A USAR.
				
				//SE DETERMINA QUE TODAS LAS CAJAS DE TEXTOS NO ESTEN VACIAS
				if(t_codigo.getText().equals("")||(t_nombre.getText().equals(""))||(t_apellido.getText().equals(""))||(t_direccion.getText().equals(""))||
						(t_edad.getText().equals(""))|| (combo_genero.getSelectedItem().toString().equals(""))) {	
				    JOptionPane.showMessageDialog(null,"Por Favor, llene Todos los Campos \n");//se abre una caja de dialogo para avisar al usuario.
					return;//En caso de estar vacias, retorna el metodo.
				}else {
				try {
					Connection conn = cc.getConnection();//SE ESTABLECE UNA CONEXION A LA BASE DE DATOS, MEDIANTE GETCONNECTION.
				   PreparedStatement stmt = conn.prepareStatement(sql);//SE PREPARA EL STATEMENT, PARA PODER EJECUTAR LA SENTENCIA.
				   int index = 1;
				   //SE AGREGAN LOS DIFERENTES VALORES DE LAS VARIABLES A LA CONSULTA.
					stmt.setString(index++, cod);//columna #1
					stmt.setString(index++, nom);//columna #2
					stmt.setString(index++, ape);//columna #3
					stmt.setString(index++, gen);//columna #4
					stmt.setString(index++, edad);//columna #5
					stmt.setString(index, direc);//columna #6
				    stmt.executeUpdate();//se ejecuta la actualizacion a la tabla participante.
				    //Al guardar los datos, sale una ventana informando el hecho.
					JOptionPane.showMessageDialog(null, "Datos Guardados Exitosamente!");
					
				}catch(SQLException el) {
					
					el.printStackTrace();//SE LANZA LA PILA DE EXCEPCIONES EN CASO DE ERROR.
				}
			}
				//LUEGO DE GUARDAR LOS DATOS, HACEMOS LOS SIGUIENTES AJUSTES.
				t_codigo.setText("");//SE LIMPIAN LAS CAJAS DE TEXTO.
				t_nombre.setText("");
				t_apellido.setText("");
				t_edad.setText("");
				t_direccion.setText("");
				
				btn_Nuevo.setEnabled(true);//SE ACTIVA EL BOTON NUEVO.
				btnGuardar.setEnabled(false);//SE DESACTIVA EL BOTON GUARDAR.
				
				//SE DESACTIVAN CADA UNA DE LAS CAJAS DE TEXTO, INCLUYENDO LA CAJA DE GENERO.
				t_codigo.setEnabled(false);
				t_nombre.setEnabled(false);
				t_apellido.setEnabled(false);
				t_edad.setEnabled(false);
				t_direccion.setEnabled(false);
				combo_genero.setEnabled(false);
				}}
		);
	
		//EN ESTA PARTE SE LE AGREGAN LAS DIFERENTES CARACTERISTICAS AL BOTON Guardar. 
		btnGuardar.setBackground(new Color(0, 0, 128));
		btnGuardar.setRolloverIcon(new ImageIcon(Formulario.class.getResource("/Botones/save_roll.png")));
		btnGuardar.setPressedIcon(new ImageIcon(Formulario.class.getResource("/Botones/save_press.png")));
		btnGuardar.setIcon(new ImageIcon(Formulario.class.getResource("/Botones/save_norm.png")));
		btnGuardar.setFocusPainted(false);
		btnGuardar.setContentAreaFilled(false);
		btnGuardar.setBorderPainted(false);
		btnGuardar.setBorder(null);
		btnGuardar.setBounds(191, 421, 103, 35);
		contentPane.add(btnGuardar);
		
		
		//SE CREA UNA NUEVA ETIQUETA Y SE LE ASIGNA LA PALABRA, Codigo.
		JLabel lblNewLabel = new JLabel("C\u00F3digo:");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(54, 143, 69, 23);
		contentPane.add(lblNewLabel);

		
		//SE CREA UNA NUEVA ETIQUETA O JLABEL, Y SE LE ASIGNA LA PALABRA, Nombre.
		JLabel lblNewLabel_1 = new JLabel(" Nombre:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(43, 177, 69, 23);
		contentPane.add(lblNewLabel_1);
		
		
        //SE CREA UNA ETIQUETA O JLABEL, QUE CONTIENE LA PALABRA Genero.
		JLabel lblNewLabel_2 = new JLabel("  G\u00E9nero:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(43, 246, 80, 23);
		contentPane.add(lblNewLabel_2);
        
		
        //SE CREA UNA NUEVA ETIQUETA QUE CONTIENE EL TEXTO, Edad.
		JLabel lblNewLabel_3 = new JLabel("Edad:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBackground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(66, 290, 46, 23);
		contentPane.add(lblNewLabel_3);
        
		//SE CREA UNA LINEA DE SEPARACION QUE DUVIDE LA VENTANA EN DOS PARTES.
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 324, 504, 2);
		contentPane.add(separator);
        
		//SE CREA UN NUEVO LABEL PARA LA ETIQUETA DE Direccion.
		JLabel lblNewLabel_4 = new JLabel(" Direcci\u00F3n:");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(28, 352, 85, 23);
		contentPane.add(lblNewLabel_4);
          
		//SE CREA UN NUEVO  TextField Y SE LE ASIGNA UNA EVENTO DE TIPO KeyTyped
		t_codigo = new JTextField();
		t_codigo.setFont(new Font("Tahoma", Font.BOLD, 12));
		t_codigo.setBackground(Color.LIGHT_GRAY);
		t_codigo.setForeground(Color.BLACK);
		t_codigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char x = e.getKeyChar();
				if(x<'0'|| x >'9') e.consume();//HACEMOS ESTE EVENTO PARA QUE SOLO PERMITA NUMEROS.
			}
		});
		//LE ASIGNAMOS UN EVENTO PARA QUE CADA QUE SE LE DE A ENTER, EL CURSOR SE DESPLASA A LA SIGUIENTE LINEA.
		t_codigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				t_codigo.transferFocus();
			}
		});
		t_codigo.setEnabled(false);
		t_codigo.setBounds(113, 144, 97, 23);
		contentPane.add(t_codigo);
		t_codigo.setColumns(10);
		
       //se crea un nuevo TextField
		t_nombre = new JTextField();
		t_nombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		t_nombre.setForeground(Color.BLACK);
		t_nombre.setBackground(Color.LIGHT_GRAY);
		t_nombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				
				char a = arg0.getKeyChar();
				if((a < 'a'||a>'z')&&(a<'A'||a>'Z')&&(a<' '||a>' ')) arg0.consume();//evento para que solo admita letras.
				
			}
		});
		t_nombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_nombre.transferFocus();
			}
		});
		t_nombre.setEnabled(false);
		t_nombre.setBounds(113, 177, 253, 23);
		contentPane.add(t_nombre);
		t_nombre.setColumns(10);

		//se crea un nuevo TextField y se le definen sus propiedades
		t_edad = new JTextField();
		t_edad.setBackground(Color.LIGHT_GRAY);
		t_edad.setForeground(Color.BLACK);
		t_edad.setFont(new Font("Tahoma", Font.BOLD, 12));//tipo de letra y tamano de las misma
		t_edad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char x = e.getKeyChar();
				if(x<'0'|| x >'9') e.consume();//HACEMOS ESTE EVENTO PARA QUE SOLO PERMITA NUMEROS.
			}
		});
		t_edad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_edad.transferFocus();
			}
		});
		t_edad.setEnabled(false);
		t_edad.setBounds(113, 290, 49, 23);
		contentPane.add(t_edad);
		t_edad.setColumns(10);
       
		//Se crea una nueva etiqueta a la que se le asigna la palabra Apellido
		JLabel lblNewLabel_5 = new JLabel("Apellido:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5.setBounds(43, 211, 69, 20);
		contentPane.add(lblNewLabel_5);

		 //Propiedades del TextField, apellido.
		t_apellido = new JTextField();
		t_apellido.setBackground(Color.LIGHT_GRAY);
		t_apellido.setForeground(Color.BLACK);
		t_apellido.setFont(new Font("Tahoma", Font.BOLD, 12));
		t_apellido.addKeyListener(new KeyAdapter() {
			//SE LE ASIGNA UN EVENTO DE TIPO KEYTYPED, PARA QUE NO ADMITA NUMEROS ESTE TEXTFIELD.
			@Override
			public void keyTyped(KeyEvent e) {
				char a = e.getKeyChar();
				if((a < 'a'||a>'z')&&(a<'A'||a>'Z')&&(a<' '||a>' ')) e.consume();//SOLO PERMITE LETRAS
			}
		});
		t_apellido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_apellido.transferFocus();//evento definido para que salte a la proxima linea al dar click a enter.
			}
		});
		//por defecto este textField estara desabilitado
		t_apellido.setEnabled(false);
		t_apellido.setBounds(113, 211, 253, 22);
		contentPane.add(t_apellido);
		t_apellido.setColumns(10);

		
		//Propiedades del boton Nuevo
		btn_Nuevo.setBackground(new Color(0, 0, 139));
		btn_Nuevo.setRolloverIcon(new ImageIcon(Formulario.class.getResource("/Botones/nuevo_roll.png")));//imagen del boton
		btn_Nuevo.setPressedIcon(new ImageIcon(Formulario.class.getResource("/Botones/nuevo_press.png")));//forma al presionar el boton
		btn_Nuevo.setIcon(new ImageIcon(Formulario.class.getResource("/Botones/nuevo_norm.png")));//forma al separar el puntero del boton
		btn_Nuevo.setFocusPainted(false);
		btn_Nuevo.setContentAreaFilled(false);
		btn_Nuevo.setBorderPainted(false);
		btn_Nuevo.setBorder(null);
		btn_Nuevo.setBounds(43, 421, 103, 35);
		contentPane.add(btn_Nuevo);

		/*EN ESTA PARTE CREAMOS EL BOTON SALIR Y LE ASIGNAMOS UN EVENTO*/
		JButton btn_Salir = new JButton("");
		btn_Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se pregunta al usuario que si de verdad desea salir, una vez pulsado el boton salir
				int jm= JOptionPane.showConfirmDialog(null, "Seguro que desea salir?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				//si su respues es si, entonces se cierra el formulario.
				if(jm == JOptionPane.YES_OPTION) {
				dispose();
				}
			}
		});
		//Propiedades del boton salir
		btn_Salir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_Salir.setRolloverIcon(new ImageIcon(Formulario.class.getResource("/Botones/salir_roll.png")));//imagen de boton
		btn_Salir.setPressedIcon(new ImageIcon(Formulario.class.getResource("/Botones/salir_press.png")));//imagen al presionar
		btn_Salir.setIcon(new ImageIcon(Formulario.class.getResource("/Botones/salir_norm.png")));//imagen boton norm
		btn_Salir.setFocusPainted(false);
		btn_Salir.setContentAreaFilled(false);
		btn_Salir.setBorderPainted(false);
		btn_Salir.setBorder(null);//no tiene bordes
		btn_Salir.setBounds(335, 421, 103, 35);
		contentPane.add(btn_Salir);
        
		//se agrega un nuevo textField y sus diferentes propiedades.
		t_direccion = new JTextField();
		t_direccion.setFont(new Font("Tahoma", Font.BOLD, 12));//tipo de letra y tamano.
		t_direccion.setForeground(Color.BLACK);//color de letras
		t_direccion.setBackground(Color.LIGHT_GRAY);//color de fondo
		t_direccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_direccion.transferFocus();
				//se agrega un evento para que el cursor pase a la siguiente linea.
			}
		});
		
		t_direccion.setEnabled(false);
		t_direccion.setBounds(113, 353, 312, 24);
		contentPane.add(t_direccion);
		t_direccion.setColumns(10);
		
		//SE CREA LA ETIQUETA O TITULO DE MI APLICACION Y SE LE ASIGNAN LOS DIFERENTES VALORES.
		JLabel lblNewLabel_6 = new JLabel("      Pr\u00E1ctica Final.");
		lblNewLabel_6.setBackground(Color.BLACK);
		lblNewLabel_6.setFont(new Font("Stencil", Font.BOLD, 40));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(10, 36, 494, 66);
		contentPane.add(lblNewLabel_6);
		
		//SE CREA UN NUEVO JLABEL Y SE LE ASIGNA COMO FONDO DEL JPANE.
		JLabel lbFondo = new JLabel("");
		lbFondo.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFondo.setBackground(Color.BLACK);
		lbFondo.setForeground(Color.WHITE);
		lbFondo.setIcon(new ImageIcon("C:\\Users\\victor\\Desktop\\wallpaper.jpg"));
		lbFondo.setBounds(0, 0, 504, 497);
		contentPane.add(lbFondo);
		
		
		
		
		

	}
}
