package objects;

import java.io.Serializable;
import java.util.Collection;

import javax.vecmath.Vector3d;

import scene.Intersection;
import scene.Transformation;

/**
 * @author Rana Alrabeh, Tolga Bolukbasi, Aaron Heuckroth, David Klaus, and Bryant Moquist
 */
public abstract class AbstractSceneObject implements SceneObject, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Material material = new Material();
	public String name = "N/A";

	@Override
	public String getName()
	{
		return name;
	}
	
	/** Sets the name of this SceneObject, for identification in the GUI. */
	public void setName(String s){
		name = s;
	}

	@Override
	public Material getMaterial()
	{
		return material;
	}

	@Override
	public abstract Vector3d getNormalAt(Vector3d point);
	
	public abstract boolean IntersectP(Ray ray);

	public abstract boolean Intersect(Ray ray, Intersection inter);

	@Override
	public Collection<? extends SceneObject> getChildren()
	{
		return null;
	}

}
