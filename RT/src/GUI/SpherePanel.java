/**
 *
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;

import objects.Sphere;
import raytracer.Renderer;
import scene.PreviewScene;
import scene.Scene;

/**
 * @author Rana Alrabeh, Tolga Bolukbasi, Aaron Heuckroth, David Klaus, and Bryant Moquist
 */
public class SpherePanel extends JPanel
{
	Sphere mySphere;
	Renderer previewRenderer = new Renderer();
	JPanel leftPanel;
	JPanel rightPanel;

	SphereInfoPanel infoPanel;
	MaterialPanel matPanel;

	JButton updateButton;

	PreviewPanel previewPanel;

	JPanel lowerLeftPanel;

	BufferedImage preview = null;

	static JFrame myFrame = null;

	public static void main(String[] args)
	{
		Sphere demoSphere = new Sphere();
		float radius = .5f;
		Vector3d position = new Vector3d(0, 0, 2);
		AxisAngle4d rotation = new AxisAngle4d(0, 0, 0, 0);
		demoSphere.setTransform(new Vector3d(radius, radius, radius), position, rotation);

		demoSphere.material.diffuseColor = new Vector3d(.8, .15, .15);
		JFrame testFrame = new JFrame("Scene Object Information: Sphere");
		// testFrame.setMinimumSize(new Dimension(650, 600));
		// testFrame.setPreferredSize(new Dimension(650, 600));
		testFrame.setResizable(true);
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SpherePanel testPanel = new SpherePanel(demoSphere);
		testFrame.add(testPanel);
		testFrame.pack();
		testFrame.setVisible(true);
		myFrame = testFrame;
	}

	public SpherePanel(Sphere targetSphere)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		mySphere = targetSphere;
		setPreviewDefaults();

		setupPanels();
		setVisible(true);
	}

	private void setPreviewDefaults()
	{
		Renderer.setOptionAntialiasing(1);
		Renderer.setOptionShadow(1);
		Renderer.setOptionWidth(300);
		Renderer.setOptionHeight(300);
	}

	public void updatePreviewImage()
	{
		try
		{
			Scene preScene = new PreviewScene(mySphere);
			Scene.writeSceneToFile(preScene, "sceneTest.scn");
			Scene preScene2 = Scene.readSceneFromFile("sceneTest.scn");
			try
			{
				preview = Renderer.renderScene(preScene2);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		previewPanel.setImage(preview);

	}

	public void setupPanels()
	{
		ActionListener up = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateSphere();
			}
		};

		infoPanel = new SphereInfoPanel(mySphere);
		infoPanel.mySpherePanel = this;
		infoPanel.addFieldListeners(up);
		// infoPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
		matPanel = new MaterialPanel(mySphere.material);
		matPanel.addFieldListeners(up);

		rightPanel = matPanel;
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(infoPanel);

		previewPanel = new PreviewPanel(preview);
		updatePreviewImage();
		lowerLeftPanel = new JPanel();
		lowerLeftPanel.setLayout(new BorderLayout());
		lowerLeftPanel.add(previewPanel);
		rightPanel.add(lowerLeftPanel);

		add(leftPanel);
		add(rightPanel);
	}

	public void updateSphere()
	{
		infoPanel.updateSphereInfo();
		matPanel.updateMaterialInfo();
		updatePreviewImage();
		if (myFrame != null)
		{
			myFrame.pack();
		}

		System.out.println(mySphere.toString());
	}
}
