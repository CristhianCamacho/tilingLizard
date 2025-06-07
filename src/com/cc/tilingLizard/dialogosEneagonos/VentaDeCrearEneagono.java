package com.cc.tilingLizard.dialogosEneagonos;

import com.cc.tilingLizard.paneles.Elementos_UI;
import com.cc.tilingLizard.paneles.Panel_calculable;
import com.cc.tilingLizard.paneles.panel_disenio.Panel_patron_disenio;
import com.cc.tilingLizard.paneles.panel_resultado.Panel_resultado;
import com.cc.tilingLizard.utils.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentaDeCrearEneagono extends JFrame{
	public static VentaDeCrearEneagono instance;
	
	Elementos_UI elementosUI;
	
	JComboBox jcb_TipoDeEneagono;
	String TIPO1="tipo1";
	String TIPO2="tipo2";
	JComboBox jcb_NroDePuntas;
	JComboBox jcb_lado;
	JComboBox jcb_salto;

	JComboBox jcb_sentido_de_agregado;

	JComboBox jcb_panel_12_34_45;

	JCheckBox jcb_agregar_al_final;
	
	private VentaDeCrearEneagono()
	{
		super("VentaDeEneagono");
		elementosUI = Elementos_UI.getInstance();
		initialize();
	}
	
	public static VentaDeCrearEneagono getInstance()
	{
		if(instance == null)
		{
			instance = new VentaDeCrearEneagono();
		}
		
		return instance;
	}
	
	private void initialize()
	{
		setLayout( new BoxLayout( this.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panelDeTypoEeneagono = crearPanelDeTypoEeneagono();
		add(panelDeTypoEeneagono);
		
		JPanel panelNroDePuntas = crearPanelNroDePuntas();
		add(panelNroDePuntas);
		
		JPanel panelLongitudLado = crearLongitudLado();
		add(panelLongitudLado);
		
		JPanel panelSalto = crearSalto();
		add(panelSalto);

		JPanel panelSentidoDeAgregado = crearPanelSentidoDeAgregado();
		add(panelSentidoDeAgregado);

		JPanel panelPanelPatronIncial_O_PanelPatronRecursivo = crearPanelPatronIncial_O_PanelPatronRecursivo();
		add(panelPanelPatronIncial_O_PanelPatronRecursivo);

		JPanel panelCheckAgregarAlFinal = crearPanelCheckAgregarAlFInal();
		add(panelCheckAgregarAlFinal);
		
		JPanel panelCalcular = crearPanelCalcular();
		add(panelCalcular);
		
		darTamanio();
		
		this.setVisible(true);
	}
	
	private JPanel crearPanelDeTypoEeneagono()
	{
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		TitledBorder titleNombreModelo;
		titleNombreModelo = BorderFactory.createTitledBorder("Tipo De Eneagono para el salto");
		result.setBorder(titleNombreModelo);
		result.setLayout(new BorderLayout());
		
		String elementos[]={TIPO1, TIPO2};
		
		jcb_TipoDeEneagono = new JComboBox(elementos);
		jcb_TipoDeEneagono.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) {
					String sItem = (String) jcb_TipoDeEneagono.getSelectedItem();
				}
			}	
		);
		jcb_TipoDeEneagono.setSelectedIndex(1);
		result.add(jcb_TipoDeEneagono);
		
		return result;
	}
	
	private JPanel crearPanelNroDePuntas()
	{
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		TitledBorder titleNombreModelo;
		titleNombreModelo = BorderFactory.createTitledBorder("Nro De Puntas ");
		result.setBorder(titleNombreModelo);
		result.setLayout(new BorderLayout());
		
		int max = 200;
		String elementos[]=new String[max];
		for(int i=0; i<elementos.length ; i++ )
			elementos[i]=""+(i+1);
		
		jcb_NroDePuntas = new JComboBox(elementos);
		jcb_NroDePuntas.setSelectedIndex(6-1);
		jcb_NroDePuntas.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0) {
						String sItem = (String) jcb_NroDePuntas.getSelectedItem();
						
						int max = Constants.getMaxSalto(Integer.parseInt((String)jcb_NroDePuntas.getSelectedItem()));
						String elementos[]=new String[max];
						for(int i=0; i<elementos.length ; i++ )
						{
							elementos[i]=""+(i+1);
							//System.out.println(this.getClass()+" elementos["+i+"]"+elementos[i]);
						}	
						
						JPanel c = (JPanel) VentaDeCrearEneagono.this.jcb_salto.getParent();
						c.remove(jcb_salto);
						jcb_salto = new JComboBox(elementos);
						jcb_salto.setSelectedIndex(max-1);
						c.add(jcb_salto);
						
						c.updateUI();
					}
				}	
			);
		result.add(jcb_NroDePuntas);
		
		return result;
	}
	
	private JPanel crearSalto()
	{
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		TitledBorder titleNombreModelo;
		titleNombreModelo = BorderFactory.createTitledBorder("Salto entre puntos");
		result.setBorder(titleNombreModelo);
		result.setLayout(new BorderLayout());
		
		int max = Constants.getMaxSalto(Integer.parseInt((String)jcb_NroDePuntas.getSelectedItem()));
		String elementos[]=new String[max];
		for(int i=0; i<elementos.length ; i++ )
			elementos[i]=""+(i+1);
		
		jcb_salto = new JComboBox(elementos);
		//jcb_salto.setSelectedIndex(max-1);
		jcb_salto.setSelectedIndex(0);
		jcb_salto.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) {
					String sItem = (String) jcb_salto.getSelectedItem();
				}
			}	
		);
		result.add(jcb_salto);
		
		return result;
	}
	
	private JPanel crearLongitudLado()
	{
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		TitledBorder titleNombreModelo;
		titleNombreModelo = BorderFactory.createTitledBorder("Longitud Lado");
		result.setBorder(titleNombreModelo);
		result.setLayout(new BorderLayout());
		
		int max = 1000;
		String elementos[]=new String[max];
		for(int i=0; i<elementos.length ; i++ )
			elementos[i]=""+(i+1);
		
		jcb_lado = new JComboBox(elementos);
		jcb_lado.setSelectedIndex(99);
		jcb_lado.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) {
					String sItem = (String) jcb_lado.getSelectedItem();
				}
			}	
		);
		result.add(jcb_lado);
		
		return result;
	}

	public JPanel crearPanelSentidoDeAgregado() {

		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		TitledBorder titleSentidoDeAgregado;
		titleSentidoDeAgregado = BorderFactory.createTitledBorder("Sentido De Agregado");
		result.setBorder(titleSentidoDeAgregado);

		String elementos[]={"Horario","Antihorario"};

		jcb_sentido_de_agregado = new JComboBox(elementos);
		jcb_sentido_de_agregado.setSelectedIndex(0);
		jcb_sentido_de_agregado.addActionListener(new ActionListener()
												  {
													  public void actionPerformed(ActionEvent arg0) {
														  String sItem = (String) jcb_salto.getSelectedItem();
													  }
												  }
		);
		result.add(jcb_sentido_de_agregado);

		return result;
	}

	public JPanel crearPanelPatronIncial_O_PanelPatronRecursivo() {

		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		TitledBorder titlePatronIncial_O_PatronRecursivo;
		titlePatronIncial_O_PatronRecursivo = BorderFactory.createTitledBorder("agregar al PatronIncial O PatronRecursivo");
		result.setBorder(titlePatronIncial_O_PatronRecursivo);

		String elementos[]={"Panel 12","Panel 34","Panel 56", "Panel Resultado"};

        jcb_panel_12_34_45 = new JComboBox(elementos);
        jcb_panel_12_34_45.setSelectedIndex(0);
		result.add(jcb_panel_12_34_45);

		return result;

	}

	public JPanel crearPanelCheckAgregarAlFInal() {


		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		TitledBorder titlePatronIncial_O_PatronRecursivo;
		titlePatronIncial_O_PatronRecursivo = BorderFactory.createTitledBorder("agregar al final de los pts actuales");
		result.setBorder(titlePatronIncial_O_PatronRecursivo);

		jcb_agregar_al_final = new JCheckBox("Agregar al final");
		jcb_agregar_al_final.setSelected(false);

		result.add(jcb_agregar_al_final);

		return result;
	}
	
	private JPanel crearPanelCalcular()
	{
		JPanel result = new JPanel();
		
		result.setLayout(new BorderLayout());
		TitledBorder titleNombreModelo;
		titleNombreModelo = BorderFactory.createTitledBorder(Panel_resultado.CALCULAR);
		result.setBorder(titleNombreModelo);
		result.setLayout(new BorderLayout());
		
		JButton jb_botonCalcular =new JButton(Panel_resultado.CALCULAR);
		jb_botonCalcular.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0) {
						instance.setVisible(false);

						Panel_calculable agregarAlPanel;
                        switch (jcb_panel_12_34_45.getSelectedIndex()) {
                            case 0:
                                agregarAlPanel = elementosUI.panel_patron_12;
                                break;
                            case 1:
                                agregarAlPanel = elementosUI.panel_patron_34;
                                break;
                            case 2:
                                agregarAlPanel = elementosUI.panel_patron_56;
                                break;
                            case 3:
                                agregarAlPanel = elementosUI.panel_resultado;
                                break;
                            default:
                                agregarAlPanel = elementosUI.panel_patron_12;
                                break;
                        }
						
						String sLado = (String) jcb_lado.getSelectedItem();
						String sNroPuntas = (String) jcb_NroDePuntas.getSelectedItem();
						String sSalto = (String) jcb_salto.getSelectedItem();
						
						int lado = Integer.parseInt(sLado);
						int nroPuntas = Integer.parseInt(sNroPuntas); 
						int salto = Integer.parseInt(sSalto); 
						
						String tipoEstrella = (String) jcb_TipoDeEneagono.getSelectedItem();
						if(tipoEstrella.equalsIgnoreCase(TIPO1))
						{
							agregarAlPanel.calcularEneagono(nroPuntas, lado, salto,
									jcb_sentido_de_agregado.getSelectedIndex(), jcb_agregar_al_final.isSelected());
						}
						if(tipoEstrella.equalsIgnoreCase(TIPO2))
						{
							agregarAlPanel.calcularEneagono1(nroPuntas, lado, salto,
									jcb_sentido_de_agregado.getSelectedIndex(), jcb_agregar_al_final.isSelected());
						}

					}
				}
		);
		result.add(jb_botonCalcular);
		
		return result;
	}
	
	private void darTamanio()
	{
		this.pack();
		
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension ventanaDim = this.getSize();
		
		setLocation((int)( (dim.getWidth()-ventanaDim.getWidth())/2 ),
					(int)( (dim.getHeight()-ventanaDim.getHeight())/2 ));
	}
	
	public void setElementosUI( Elementos_UI elementosUI )
	{
		this.elementosUI = elementosUI;
	}
	
	public void mostrar()
	{
		this.setVisible(true);		
	}

}
