package task_gamification.task_manager;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.entity.User;
import task_gamification.helpers.GetFilePath;

import java.util.List;

public class Story {
	private String getStoryFilePath;

	private CSVReader csvReader;

	public Story () {
		csvReader = new CSVReader();
		getStoryFilePath = GetFilePath.STORY_FILE_PATH;
	}

	/**
	 * Fetches the story line for a specific level.
	 *
	 * @param level The level for which the story line is to be fetched.
	 * @return The story line associated with the given level.
	 */
	public String updateStory(int level) {
		List<List<String>> storyData = csvReader.readCSV(getStoryFilePath);
		if (level >= 0 && level < storyData.size() && storyData.get(level).size() > 2) {
			String storyLine = storyData.get(level).get(2);
			if (storyLine.isEmpty()) {
				return "";
			}
			return storyLine;
		}
		return "";
	}
}
