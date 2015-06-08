package main;

import algoritmos.Astar;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import elementos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class MainGrafico extends JFrame {
    private static Grafo grafo;
    private ArrayList<Encomenda> encs;
    private JTextArea jTextArea;
    private JScrollPane scroll;
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private Hashtable<No, Object> vertexes;
    private ArrayList<Object> cells;

    public MainGrafico() {

        initInterface();

        graph = new mxGraph();
        vertexes = new Hashtable<>();
        cells = new ArrayList<Object>();
        jTextArea = new JTextArea(1,110);
        jTextArea.setEditable(false);
        scroll = new JScrollPane(jTextArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(1200, 550));
        graphComponent.setConnectable(false);
        graphComponent.getGraphControl().addMouseListener(new MyMouseListener());
        getContentPane().add(graphComponent);
        getContentPane().add(jTextArea, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }


    private void initInterface() {
        setLayout(new FlowLayout(FlowLayout.LEADING));

        JButton carregarMapa = new JButton("Carregar Mapa");
        getContentPane().add(carregarMapa);
        carregarMapa.addActionListener(new CarregarMapaActionListener());

        JButton carregarInventario = new JButton("Carregar Inventário");
        getContentPane().add(carregarInventario);
        carregarInventario.addActionListener(new CarregarInventarioActionListener());

        JButton adicionarVertice = new JButton("Adicionar Local");
        getContentPane().add(adicionarVertice);
        adicionarVertice.addActionListener(new AdicionarVerticeActionListener());

        JButton removerVertice = new JButton("Remover Local");
        getContentPane().add(removerVertice);
        removerVertice.addActionListener(new RemoverVerticeActionListener());

        JButton adicionarAresta = new JButton("Adicionar Caminho");
        getContentPane().add(adicionarAresta);
        adicionarAresta.addActionListener(new AdicionaArestaActionListener());

        JButton removerAresta = new JButton("Remover Caminho");
        getContentPane().add(removerAresta);
        removerAresta.addActionListener(new RemoverArestaActionListener());

        JButton calcularPercurso = new JButton("Calcular Percurso");
        getContentPane().add(calcularPercurso);
        calcularPercurso.addActionListener(new CalcularPercursoActionListener());

        JButton modificarVeiculo = new JButton("Modificar Caracteristicas Veiculo");
        getContentPane().add(modificarVeiculo);
        modificarVeiculo.addActionListener(new ModificarVeiculoActionListener());
    }


    private void desenharMapa() {

        Object parent = graph.getDefaultParent();
        ArrayList<No> nos = new ArrayList<No>(grafo.getNos().values());

        for (Object cell : cells) {
            graph.getModel().remove(cell);
        }

        cells.clear();
        graph.getModel().beginUpdate();

        try {
            for (No no : nos) {
                String color;
                if (no.noCaminho())
                    color = ";fillColor=red";
                else
                color = "";
                Object v1 = graph.insertVertex(parent, null, no.getNome(), no.getX(), no.getY(), 80, 30, "editable=0" + color);
                vertexes.put(no, v1);
                cells.add(v1);
            }

       for (No no : nos) {
            ArrayList<Aresta> caminhos = new ArrayList<Aresta>(no.getmapaCaminhos().values());
            for (Aresta a : caminhos) {
                String color;
                if (a.noCaminho())
                    color = "strokeColor=red";
                else
                color = "";

                Object e1 = graph.insertEdge(parent, null, a.getCusto(), vertexes.get(no), vertexes.get(a.getNoDestino()), color);
                cells.add(e1);
            }
        }
        }
        finally {
            graph.getModel().endUpdate();
        }

    }


    private boolean isInt(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;

        }
    }


    private boolean isDouble(String x) {
        try {
            Double.parseDouble(x);
            return true;
        } catch (NumberFormatException e) {
            return false;

        }
    }

    private boolean isFloat(String x) {
        try {
            Float.parseFloat(x);
            return true;
        } catch (NumberFormatException e) {
            return false;

        }
    }


    private class CarregarMapaActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnValue = fc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    grafo.lerGrafo(file.getName());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                desenharMapa();
            }
        }
    }

    private class CarregarInventarioActionListener extends Component implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnValue = fc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    encs = grafo.lerInventario(file.getName());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private class CalcularPercursoActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            Estado inicial = new Estado(grafo.getNo("A"), new Veiculo(10, 300), encs);
            Astar teste = new Astar(new Grafo(grafo.getNos()));
            ArrayList<Estado> caminhoAPercorrer = teste.executar(inicial);

            if (caminhoAPercorrer == null) {System.out.println("Ocorreu um erro! Poderá não ter combustivel para partir."); return;}

            jTextArea.append("## Caminho percorrido {No, CargaAtual, EncomendasPorRecolher, Gasolina} ##\n\n");
            System.out.println("\n\n## Caminho percorrido {No, CargaAtual, EncomendasPorRecolher, Gasolina}");
            for (Estado est : caminhoAPercorrer) {
                System.out.println(" {" + est.getNoAtual().getNome() + " , " + est.getVeiculo().getCargaAtual() + " , " + est.getVolumeEncomendasPorRecolher() + " , " + est.getVeiculo().getGasolinaAtual() + "} ");
                jTextArea.append(" {" + est.getNoAtual().getNome() + " , " + est.getVeiculo().getCargaAtual() + " , " + est.getVolumeEncomendasPorRecolher() + " , " + est.getVeiculo().getGasolinaAtual() + "} ");
                grafo.getNo(est.getNoAtual().getNome()).setNoCaminho(true);
            }

            desenharMapa();
        }
    }

    private class AdicionarVerticeActionListener implements ActionListener {

        @Override
            public void actionPerformed(ActionEvent e) {
                String nome = JOptionPane.showInputDialog("Qual o nome do local?");
                if (nome == null)
                    return;

                String x, y;
                x = JOptionPane.showInputDialog("Qual a posição em x do local?");
                if (x == null || !isInt(x))
                    return;

                y = JOptionPane.showInputDialog("Qual a posição em y do local?");
                if (y == null || !isInt(y))
                    return;

                No novoNo = new No(nome, Integer.parseInt(x), Integer.parseInt(y)) {
                };

                grafo.addNo(novoNo);
                desenharMapa();
            }
        }

        private class RemoverVerticeActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = JOptionPane.showInputDialog("Qual o nome do local a remover?");
                if (nome == null)
                    return;

                grafo.removeNoNome(nome);
                desenharMapa();
            }
        }

        private class AdicionaArestaActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                String from = JOptionPane.showInputDialog("Qual o nome do local origem do caminho?");
                String to = JOptionPane.showInputDialog("Qual o nome do local destino?");

                No nFrom = grafo.getNo(from);
                No nTo = grafo.getNo(to);

                if (nFrom == null || nTo == null)
                    return;

                String nome, custo;
                nome = nFrom.getNome()+"-"+nTo.getNome();

                custo = JOptionPane.showInputDialog("Qual o custo do caminho?");
                if (custo == null || !isDouble(custo))
                    return;

                Aresta a = new Aresta(nome, Double.parseDouble(custo), nFrom, nTo);

                nFrom.addAresta(a);
                desenharMapa();
            }

        }

    private class RemoverArestaActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String from = JOptionPane.showInputDialog("Qual o nome do local origem do caminho?");
            String to = JOptionPane.showInputDialog("Qual o nome do local onde termina o caminho?");

            No nFrom = grafo.getNo(from);

            Aresta a = new Aresta(from+"-"+to);

            nFrom.removeAresta(a);
            desenharMapa();
        }
    }

    private class ModificarVeiculoActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Veiculo veiculo = grafo.getVeiculo();
            String limiteComb = JOptionPane.showInputDialog("Qual o novo limite de combustivel maximo?");

            if (limiteComb != null && isFloat(limiteComb))
                veiculo.setGasolinaMáx(Float.parseFloat(limiteComb));

            String cap = JOptionPane.showInputDialog("Qual a capacidade máxima do veiculo?");
                veiculo.setCargaMáx(Integer.parseInt(cap));
        }
    }

    private class MyMouseListener extends MouseAdapter {

        Object selected;

        public MyMouseListener() {
            selected = null;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Set<No> nos = vertexes.keySet();

            for (No no : nos) {
                Object n = vertexes.get(no);

                if (n == selected) {
                    no.setX(e.getX());
                    no.setY(e.getY());
                }
            }

            selected = null;
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            selected = graphComponent.getCellAt(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

    }

    public static void main(String[] args) throws IOException {
        grafo = new Grafo();
        new MainGrafico();
    }
}




