package application.entity;

import java.util.Collection;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import application.Guiable;
import application.Tag;
import javolution.util.FastTable;

public abstract class Entity implements Guiable {

	private String name = "unnamed";
	private final FastTable<Tag> tags = new FastTable<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public boolean containInTagsOrName(String contains) {
		if (contains == null || contains.isEmpty())
			return true;

		contains = contains.toLowerCase();

		if (name.toLowerCase().contains(contains))
			return true;

		for (Tag tag : tags) {
			if (tag.key.toLowerCase().contains(contains) || tag.value.toLowerCase().contains(contains))
				return true;
		}
		return false;
	}

	public boolean containsAllTagKeys(Collection<String> containsAll) {
		return containsAll.stream().allMatch(contains -> {
			for (Tag tag : tags) {
				if (tag.key.toLowerCase().equals(contains.toLowerCase())) {
					return true;
				}
			}
			return false;
		});
	}

	@Override
	public String toString() {
		String result = name;
		for (Tag tag : tags) {
			result += " " + tag;
		}
		return result;
	}

	@Override
	public Node detailsGui() {
		return new VBox() {
			{
				getChildren().add(new Label(name));
				FastTable<String> tagKeys = new FastTable<String>();
				for (Tag tag : tags) {
					tagKeys.add(tag.key);
				}
				getChildren().add(new Label("Tags: " + String.join(", ", tagKeys)));
			}
		};
	}

	@Override
	public Node scaleableElement() {
		return new StackPane() {
			{
				getChildren().add(new Rectangle() {
					{
						autosize();
					}
				});
				getChildren().add(new Label(name){
					{
						autosize();
					}
				});
			}
		};
	}

	@Override
	public Node graphicalRepräsentation() {
		 return new ImageView(new Image(ClassLoader.getSystemResourceAsStream("ressources/icon.png")));
	}

}
