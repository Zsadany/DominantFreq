package com.dominantfreq.display.model;

import java.io.File;

import com.dominantfreq.display.model.handlers.ButtonHandlers;
import com.dominantfreq.display.model.styles.ButtonStyles;
import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.dataaccess.EcgInfo;
import com.dominantfreq.service.bulkprocess.EcgBulkProcessingDaemon;
import com.dominantfreq.service.bulkprocess.EcgProcessingRequest;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EcgItem extends TitledPane {

	private static final EcgBulkProcessingDaemon PROCESSOR = EcgBulkProcessingDaemon.getInstance();

	// DATA
	private final File directory;
	private final String fileName;

	// Events
	private final LocalHandlers localHandlers;

	// UI elements
	private StackPane contentStack;
	private final VBox infoContent;
	private VBox resultContent;
	private Button processButton;
	private Button clearButton;
	private ProgressIndicator loadIndicator;

	// data
	private EcgAnalysis analysis;

	public EcgItem(EcgInfo info) {
		directory = info.getDirectory();
		fileName = info.getEcgName();
		localHandlers = new LocalHandlers();

		infoContent = createInfoContainer(info);
		resultContent = createResultContainer();
		loadIndicator = createProgressIndicator();
		contentStack = assembleContentStack(infoContent, resultContent, loadIndicator);

		setContent(contentStack);
		setText("Dir.: " + info.getDirectory() + "   File: " + info.getEcgName() + "  Patient: " + info.getPatient());
		setEffect(new DropShadow());
		setInfoView();
	}

	private VBox createInfoContainer(EcgInfo info) {
		VBox infoContainer = new VBox();
		infoContainer.getChildren().add(info.renderToGridPane());
		infoContainer.getChildren().add(new Separator());
		infoContainer.getChildren().add(createProcessButton("Process ECG"));
		infoContainer.getChildren().add(new Separator());
		return infoContainer;
	}

	private VBox createResultContainer() {
		VBox resultContainer = new VBox();
		resultContainer.setVisible(false);
		return resultContainer;
	}

	private StackPane assembleContentStack(Node... nodes) {
		StackPane stack = new StackPane();
		for (Node node : nodes) {
			stack.getChildren().add(node);
		}
		return stack;
	}

	public String getName() {
		return fileName;
	}

	public void sendProcessingRequest() {
		EcgProcessingRequest request = new EcgProcessingRequest(directory, fileName);
		PROCESSOR.sendProcessingRequest(request);
		setLoadingView();
	}

	public void setAndShowResult(EcgAnalysis result) {
		analysis = result;
		ObservableList<Node> results = resultContent.getChildren();
		if (!results.isEmpty())
			results.removeAll(results);
		results.add(result.renderToGridPane());
		results.add(new Separator());
		HBox buttonRow = new HBox();
		buttonRow.getChildren().add(createProcessButton("Reprocess Ecg"));
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		buttonRow.getChildren().add(separator);
		buttonRow.getChildren().add(createClearButton("Clear Result"));
		results.add(buttonRow);
		results.add(new Separator());
		setResultView();
	}

	private Button createProcessButton(String name) {
		processButton = new Button(name);
		processButton.setAlignment(Pos.CENTER_LEFT);
		processButton.setStyle(ButtonStyles.PROCESS_BUTTON_STYLE.toString());
		processButton.setEffect(new DropShadow());
		processButton.setOpacity(0.7);
		processButton.setOnMouseEntered(ButtonHandlers.buttonMouseEnteredHandler);
		processButton.setOnMouseExited(ButtonHandlers.buttonMouseExitedHandler);
		processButton.setOnAction(localHandlers.processButtonClickHandler);
		return processButton;
	}

	private Button createClearButton(String name) {
		clearButton = new Button(name);
		clearButton.setStyle(ButtonStyles.CLOSE_BUTTON_STYLE.toString());
		clearButton.setEffect(new DropShadow());
		clearButton.setAlignment(Pos.CENTER_RIGHT);
		clearButton.setOpacity(0.7);
		clearButton.setOnMouseEntered(ButtonHandlers.buttonMouseEnteredHandler);
		clearButton.setOnMouseExited(ButtonHandlers.buttonMouseExitedHandler);
		clearButton.setOnAction(localHandlers.clearButtonClickHandler);
		return clearButton;
	}

	private ProgressIndicator createProgressIndicator() {
		loadIndicator = new ProgressIndicator();
		loadIndicator.setVisible(false);
		return loadIndicator;
	}

	private void setLoadingView() {
		clearView();
		loadIndicator.setVisible(true);
	}

	private void setInfoView() {
		clearView();
		infoContent.setVisible(true);
	}

	private void setResultView() {
		clearView();
		resultContent.setVisible(true);
	}

	private void clearView() {
		ObservableList<Node> contents = contentStack.getChildren();
		for (Node content : contents) {
			content.setVisible(false);
		}
	}

	private final class LocalHandlers {
		private final EventHandler<ActionEvent> processButtonClickHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				sendProcessingRequest();
			}
		};
		private final EventHandler<ActionEvent> clearButtonClickHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				setInfoView();
			}
		};
	}

	public EcgAnalysis getEcgAnalysis() {
		return analysis;
	}
}
