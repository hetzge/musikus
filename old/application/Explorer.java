package application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javolution.util.FastTable;
import application.entity.Entity;
import application.entity.EntityType;

public class Explorer extends VBox {

	private final FastTable<Entity> entities = new FastTable<Entity>();
	private final FastTable<Entity> visibleEntities = new FastTable<Entity>();
	private final Pane explorerTreeViewPane = new Pane();
	private final SearchTextField searchTextField;
	private final TagCloud tagCloud;

	public Explorer() {
		autosize();

		searchTextField = new SearchTextField();
		tagCloud = new TagCloud();

		getChildren().add(searchTextField);
		getChildren().add(tagCloud);
		getChildren().add(explorerTreeViewPane);
	}

	public void setEntities(Collection<Entity> entities) {
		this.entities.clear();
		this.entities.addAll(entities);
		setVisibleEntities(entities);
	}

	public void setVisibleEntities(Collection<Entity> entities) {
		visibleEntities.clear();
		visibleEntities.addAll(entities);
		explorerTreeViewPane.getChildren().clear();
		explorerTreeViewPane.getChildren().add(new ExplorerTreeView());
	}
	
	public void filter(){
		FastTable<Entity> result1 = new FastTable<>();
		FastTable<String> selectedTags = tagCloud.getSelectedTags();
		for (Entity entity : entities) {
			if (entity.containsAllTagKeys(selectedTags)) {
				result1.add(entity);
			}
		}
		FastTable<Entity> result2 = new FastTable<>();
		for (Entity entity : result1) {
			if (entity.containInTagsOrName(searchTextField.getText())) {
				result2.add(entity);
			}
		}
		setVisibleEntities(result2);
	}

	public class TagCloud extends FlowPane {
		public TagCloud() {
			ToggleGroup toggleGroup = new ToggleGroup();
			for (String key : Tag.keys) {
				ToggleButton toggleButton = new ToggleButton(key);
				toggleButton.setOnAction(event -> {
					filter();
				});
				getChildren().add(toggleButton);
			}
		}

		public FastTable<String> getSelectedTags() {
			FastTable<String> result = new FastTable<String>();
			for (Node node : getChildren()) {
				if (node instanceof ToggleButton) {
					ToggleButton toggleButton = (ToggleButton) node;
					if (toggleButton.isSelected()) {
						result.add(toggleButton.getText());
					}
				}
			}
			return result;
		}
	}

	public class SearchTextField extends TextField {
		public SearchTextField() {
			setOnKeyTyped(event -> {
				filter();
			});
		}
	}

	public class ExplorerTreeView extends TreeView<Entity> {

		private final TreeItem<Entity> rootTreeItem;
		private final Map<Class<? extends Entity>, TreeItem<Entity>> categoryRoots = new HashMap<>();

		public ExplorerTreeView() {
			autosize();

			rootTreeItem = new TreeItem<Entity>();

			setRoot(rootTreeItem);
			setShowRoot(false);
			rootTreeItem.setExpanded(true);

			for (Entity entity : visibleEntities) {
				Class<? extends Entity> clazz = (Class<? extends Entity>) Utils.getClass(entity);
				if (!categoryRoots.containsKey(entity.getClass())) {
					TreeItem<Entity> categoryRoot = new TreeItem<>(EntityType.getEntityType(clazz));
					categoryRoots.put(clazz, categoryRoot);
					rootTreeItem.getChildren().add(categoryRoot);
				}
				TreeItem<Entity> categoryRoot = categoryRoots.get(clazz);
				categoryRoot.getChildren().add(new TreeItem<>(entity));
			}
		}

	}

}
