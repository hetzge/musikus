package application;

import javafx.scene.layout.BorderPane;
import javolution.util.FastTable;
import old.application.entity.AccordEntity;
import old.application.entity.AccordProgressionEntity;
import old.application.entity.AkzentPatternEntity;
import old.application.entity.Entity;
import old.application.entity.EntityType;
import old.application.entity.GeneratorEntity;
import old.application.entity.MelodieEntity;
import old.application.entity.PatternEntity;
import old.application.entity.ScaleEntity;
import old.application.entity.SongEntity;
import old.application.entity.SongPartEntity;
import old.application.entity.ToneEntity;

public class Application extends BorderPane {

	private final Explorer explorer;
	private final Grid grid;

	public Application() {
		new EntityType(AccordEntity.class, "Akkord", "");
		new EntityType(AccordProgressionEntity.class, "Chordprogression", "");
		new EntityType(AkzentPatternEntity.class, "Betonungsmuster", "");
		new EntityType(GeneratorEntity.class, "Generator", "");
		new EntityType(MelodieEntity.class, "Melodie", "");
		new EntityType(PatternEntity.class, "Pattern", "");
		new EntityType(SongPartEntity.class, "Songabschnitt", "");
		new EntityType(SongEntity.class, "Song", "");
		new EntityType(ToneEntity.class, "Note", "");
		new EntityType(ScaleEntity.class, "Tonleiter", "");

		FastTable<Entity> entities = new FastTable<Entity>() {
			{
				add(new AccordEntity() {
					{
						addTag(new Tag("Test", ""));
						addTag(new Tag("Privat", ""));
					}
				});
				add(new AccordEntity() {
					{
						ToneEntity toneEntity = new ToneEntity();
						toneEntity.setName("A");
						toneEntity.setTone(0);
						toneEntity.setPosition(0);
						addTones(toneEntity);
					}
				});
				add(new AccordEntity());
				add(new AccordEntity());
				add(new AccordEntity());
				add(new AccordEntity());
				add(new AccordEntity());

				add(new AccordProgressionEntity() {
					{
						addTag(new Tag("Geil", ""));
					}
				});
				add(new AccordProgressionEntity());
				add(new AccordProgressionEntity());
				add(new AccordProgressionEntity());
				add(new AccordProgressionEntity());

				add(new AkzentPatternEntity());
				add(new AkzentPatternEntity());
				add(new AkzentPatternEntity());
				add(new AkzentPatternEntity());
				add(new AkzentPatternEntity());
				add(new AkzentPatternEntity());

				add(new GeneratorEntity());
				add(new GeneratorEntity());
				add(new GeneratorEntity());
				add(new GeneratorEntity());
				add(new GeneratorEntity());
				add(new GeneratorEntity());

				add(new MelodieEntity());
				add(new MelodieEntity());
				add(new MelodieEntity());
				add(new MelodieEntity());
				add(new MelodieEntity());
				add(new MelodieEntity());
			}
		};

		explorer = new Explorer();
		explorer.setEntities(entities);
		setLeft(explorer);

		MelodieEntity melodieEntity = new MelodieEntity() {
			{
				addTones(new ToneEntity(10, 0, 5000), new ToneEntity(20, 100, 500), new ToneEntity(15, 200, 300));
			}
		};

		grid = new Grid(melodieEntity);
		setCenter(grid);

	}

}
