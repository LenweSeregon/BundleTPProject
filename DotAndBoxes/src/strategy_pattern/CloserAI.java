package strategy_pattern;

import java.awt.Dimension;
import java.util.Random;
import java.util.Vector;

import models.Border_position;
import models.Cell;
import models.Grid;

public class CloserAI implements AI_interface {

	@Override
	public boolean execute(Grid grid) {
		Vector<Dimension> closable_cell = grid.get_all_closable_cell();
		if (closable_cell.size() > 0) {
			Random randInt = new Random();

			int selected_int = randInt.nextInt(closable_cell.size());
			Dimension selected_dim = closable_cell.elementAt(selected_int);
			Border_position selected_border = grid.get_unique_free_border(
					selected_dim.width, selected_dim.height);

			grid.set_cell_border_to_owner(selected_dim.width,
					selected_dim.height, selected_border,
					grid.get_player_turn(), false);
			return true;
		} else {
			Vector<Cell> free_cells = grid.get_all_free_cell();
			Random intRand = new Random();

			int cell_selection = intRand.nextInt(free_cells.size());
			Cell target = free_cells.elementAt(cell_selection);

			Vector<Border_position> borders = grid
					.get_all_free_border_from_cell(target.get_line_index(),
							target.get_column_index());
			int border_selection = intRand.nextInt(borders.size());
			Border_position target_border = borders.elementAt(border_selection);

			grid.set_cell_border_to_owner(target.get_line_index(),
					target.get_column_index(), target_border,
					grid.get_player_turn(), false);
		}

		return true;
	}
}