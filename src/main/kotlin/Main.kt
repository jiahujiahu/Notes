import javafx.application.Application
import javafx.collections.FXCollections.observableArrayList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.*
import javafx.scene.text.TextAlignment
import javafx.stage.Stage

class Main : Application() {

    override fun start(stage: Stage) {

        var totalNotesNumber = 4
        var activeNotesNumber = 2

        // Changing the “Show archived” check box results in showing / hiding archived notes in the currently active view.
        val showArchivedCheckBox = CheckBox().apply { isSelected  = false }

        val listButton = Button("List")
        val gridButton = Button("Grid")

        val choiceBoxSort = ChoiceBox(observableArrayList("Length(asc)","Length(desc)"))

        // the toolbar contains three groups
        val groups = HBox(
            // viewsGroup
            // to switch between the views (list and grid)
            // consists of one label (“View:”) and two buttons (“List” and “Grid”).
            Label("View:").apply { padding = Insets(5.0,0.0,0.0,0.0) },
            // The 2 toolbar buttons both all the same preferred width (50 units).
            listButton.apply { prefWidth = 50.0 },
            gridButton.apply { prefWidth = 50.0 },

            // archiveGroup
            // to show or hide archived notes
            // consists of a label (“Show archived:”) and a checkbox.
            Separator().apply { orientation = Orientation.VERTICAL },
            Label("Show archived:").apply { padding = Insets(5.0,0.0,0.0,0.0) },
            showArchivedCheckBox.apply { padding = Insets(5.0,0.0,0.0,0.0) },

            // sortGroup
            // to specify the sort order
            // consists of a label (“Order by:”) and a choice box with the options “Length (asc)” and “Length (desc)”.
            Separator().apply { orientation = Orientation.VERTICAL },
            Label("Order by:").apply { padding = Insets(5.0,0.0,0.0,0.0) },
            choiceBoxSort.apply { selectionModel.select(0) }
        )
        // There are 10 units of spacing around and between all toolbar widgets.
        groups.spacing = 10.0

        // the toolbar contains a button ("Clear") that is aligned to the right
        val clearButton = Button("Clear").apply {
            prefWidth = 50.0
        }
        val toolbar = AnchorPane().apply {
            children.add(groups.apply {
                AnchorPane.setTopAnchor(this, 10.0)
                AnchorPane.setLeftAnchor(this, 10.0)
                AnchorPane.setBottomAnchor(this, 10.0)
            })
            children.add(clearButton.apply {
                AnchorPane.setTopAnchor(this, 10.0)
                AnchorPane.setRightAnchor(this, 10.0)
                AnchorPane.setBottomAnchor(this, 10.0)
            })
        }

        // The status bar displays the total number of notes and the number of notes that are active,
        // e.g., “4 notes, 2 of which are active”.
        // Please make sure that the status bar follows English grammar,
        // e.g., “1 note, 1 of which is active”.
        val statusBar = Label("$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active").apply {
            padding = Insets(10.0)
        }

        // Please make sure that your application starts with at least four notes already displayed
        val text1 = Text("This is a short note!")
        val text2 = Text("This note is slightly longer, but still manageable...")
        val text3 = Text("Active notes have a light yellow background, archived notes a light gray one.")
        val text4 = Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
                "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
                "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                "anim id est laborum.")
        // , at least one of them being active and one of them being archived.

        val checkBox1 = CheckBox().apply{ isSelected = false }
        val checkBox2 = CheckBox().apply { isSelected = true }
        val checkBox3 = CheckBox().apply { isSelected = false }
        val checkBox4 = CheckBox().apply { isSelected = true }

        val checkBoxRectangle1 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox1.selectedProperty())
        }
        val checkBoxRectangle2 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox2.selectedProperty())
        }
        val checkBoxRectangle3 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox3.selectedProperty())
        }
        val checkBoxRectangle4 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox4.selectedProperty())
        }

        val archiveLabelRectangle1 = Label("Archived")
        val archiveLabelRectangle2 = Label("Archived")
        val archiveLabelRectangle3 = Label("Archived")
        val archiveLabelRectangle4 = Label("Archived")

        val rectangle1 = HBox()
        val rectangle2 = HBox()
        val rectangle3 = HBox()
        val rectangle4 = HBox()

        val totalRectangleNotes = mutableListOf(rectangle1, rectangle2, rectangle3, rectangle4)
        val activeRectangleNotes = mutableListOf(rectangle1, rectangle3)
        val rectangleLengthMap = mutableMapOf(
            rectangle1 to text1.text.length,
            rectangle2 to text2.text.length,
            rectangle3 to text3.text.length,
            rectangle4 to text4.text.length
        )

        // The first item in list view is a “special note” that allows users to create a new note.
        // It consists of a text area and a button
        val textAreaRectangleCreate = TextArea()
        val createRectangleButton = Button("Create")
        val rectangle0 = HBox().apply {
            children.add(textAreaRectangleCreate.apply {
                // The text area fills the remaining space in the “special note”.
                prefWidthProperty().bind(stage.widthProperty().subtract(130.0))
                // “special note” height is 62.
                maxHeight = 62.0
            })
            // The button (“Create”) is on the right side of the “note”,
            // 75 units wide and 42 units high.
            children.add(createRectangleButton.apply {
                minWidth = 75.0
                minHeight = 42.0
            })
            alignment = Pos.CENTER
            // Spacing, padding and corner radii have a value of 10,
            padding = Insets(20.0)
            spacing = 10.0
            // , and is of light salmon colour.
            background = Background(
                BackgroundFill(Color.LIGHTSALMON, CornerRadii(10.0), Insets(10.0))
            )
        }

        rectangle1.apply {
            children.add(text1.apply {
                // The notes text flows left-to-right and wraps when there is not enough horizontal space.
                wrappingWidthProperty().bind(stage.widthProperty().subtract(130.0))
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
                // Notes fill the available width of the application,
                // their texts fill the available width of the note, and
                // both are as high as they need to be to display the text.
                HBox.setHgrow(this, Priority.ALWAYS)
            })
            // On the right side of each note is a checkbox (“Archived”)
            // for changing the note’s state
            // (archived if box is checked or active if box is not checked).
            children.add(checkBoxRectangle1)
            children.add(archiveLabelRectangle1)
            padding = Insets(20.0)
            // with spacing, padding and corner radii of 10.
            spacing = 10.0
            // Active notes have a light yellow background
            background = Background(
                BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
        }

        rectangle2.apply {
            children.add(text2.apply {
                wrappingWidthProperty().bind(stage.widthProperty().subtract(130.0))
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
                HBox.setHgrow(this, Priority.ALWAYS)
            })
            children.add(checkBoxRectangle2)
            children.add(archiveLabelRectangle2)
            padding = Insets(20.0)
            spacing = 10.0
            // archived notes a light gray one.
            background = Background(
                BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
        }

        rectangle3.apply {
            children.add(text3.apply {
                wrappingWidthProperty().bind(stage.widthProperty().subtract(130.0))
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
                HBox.setHgrow(this, Priority.ALWAYS)
            })
            children.add(checkBoxRectangle3)
            children.add(archiveLabelRectangle3)
            padding = Insets(20.0)
            spacing = 10.0
            background = Background(
                BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
        }

        rectangle4.apply{
            children.add(text4.apply {
                wrappingWidthProperty().bind(stage.widthProperty().subtract(130.0))
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
                HBox.setHgrow(this, Priority.ALWAYS)
            })
            children.add(checkBoxRectangle4)
            children.add(archiveLabelRectangle4)
            padding = Insets(20.0)
            spacing = 10.0
            background = Background(
                BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
        }

        // ln the list view, notes are displayed in the central area as rectangular areas
        val listView = VBox(rectangle0, rectangle1, rectangle3)

        val checkBoxSquare1 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox1.selectedProperty())
        }
        val checkBoxSquare2 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox2.selectedProperty())
        }
        val checkBoxSquare3 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox3.selectedProperty())
        }
        val checkBoxSquare4 = CheckBox().apply{
            selectedProperty().bindBidirectional(checkBox4.selectedProperty())
        }

        val archiveLabelSquare1 = Label("Archived")
        val archiveLabelSquare2 = Label("Archived")
        val archiveLabelSquare3 = Label("Archived")
        val archiveLabelSquare4 = Label("Archived")

        val square1 = VBox()
        val square2 = VBox()
        val square3 = VBox()
        val square4 = VBox()

        val label1 = Label(text1.text)
        val label2 = Label(text2.text)
        val label3 = Label(text3.text)
        val label4 = Label(text4.text)

        var totalSquareNotes = mutableListOf(square1, square2, square3, square4)
        var activeSquareNotes = mutableListOf(square1, square3)
        var squareLengthMap = mutableMapOf(
            square1 to label1.text.length,
            square2 to label2.text.length,
            square3 to label3.text.length,
            square4 to label4.text.length
        )

        // The top-left item in grid view is a “special note” that allows users to create a new note.
        //  It consists of a text area and a button, and is of light salmon colour.
        val createSquareButton = Button("Created")
        val textAreaSquareCreate = TextArea()
        val square0 = VBox().apply {
            children.add(textAreaSquareCreate.apply{
                // The text area fills the remaining space in the “special note”.
                textProperty().bindBidirectional(textAreaRectangleCreate.textProperty())
                isWrapText = true
                maxWidth = 200.0
                maxHeight = Double.MAX_VALUE
                alignment = Pos.TOP_LEFT
            })
            // The button (“Create”) is at the bottom of the “note”, 205 units wide.
            children.add(createSquareButton.apply {
                prefWidth = 205.0
            })
            // and “special note” height and width are 225.
            minWidth = 225.0
            minHeight = 225.0
            maxHeight = 225.0
            maxWidth = 225.0
            // Spacing, padding and corner radii have a value of 10,
            padding = Insets(20.0)
            spacing = 10.0
            background = Background(
                BackgroundFill(Color.LIGHTSALMON, CornerRadii(10.0), Insets(10.0))
            )
        }

        square1.apply {
            children.add(label1.apply{
                isWrapText = true
                maxWidth = 200.0
                maxHeight = Double.MAX_VALUE
                alignment = Pos.TOP_LEFT
                VBox.setVgrow(this, Priority.ALWAYS)
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
            })
            children.add(HBox().apply {
                children.add(checkBoxSquare1)
                children.add(archiveLabelSquare1)
                spacing = 10.0
            })
            minWidth = 225.0
            minHeight = 225.0
            maxHeight = 225.0
            maxWidth = 225.0
            padding = Insets(20.0)
            spacing = 10.0
            backgroundProperty().bindBidirectional(rectangle1.backgroundProperty())
        }
        square2.apply {
            children.add(label2.apply{
                isWrapText = true
                maxWidth = 200.0
                maxHeight = Double.MAX_VALUE
                alignment = Pos.TOP_LEFT
                VBox.setVgrow(this, Priority.ALWAYS)
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
            })
            children.add(HBox().apply {
                children.add(checkBoxSquare1)
                children.add(archiveLabelSquare1)
                spacing = 10.0
            })
            minWidth = 225.0
            minHeight = 225.0
            maxHeight = 225.0
            maxWidth = 225.0
            padding = Insets(20.0)
            spacing = 10.0
            backgroundProperty().bindBidirectional(rectangle2.backgroundProperty())
        }
        square3.apply {
            children.add(label3.apply{
                isWrapText = true
                maxWidth = 200.0
                maxHeight = Double.MAX_VALUE
                alignment = Pos.TOP_LEFT
                VBox.setVgrow(this, Priority.ALWAYS)
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
            })
            children.add(HBox().apply {
                children.add(checkBoxSquare1)
                children.add(archiveLabelSquare1)
                spacing = 10.0
            })
            minWidth = 225.0
            minHeight = 225.0
            maxHeight = 225.0
            maxWidth = 225.0
            padding = Insets(20.0)
            spacing = 10.0
            backgroundProperty().bindBidirectional(rectangle3.backgroundProperty())
        }
        square4.apply {
            children.add(label4.apply{
                isWrapText = true
                maxWidth = 200.0
                maxHeight = Double.MAX_VALUE
                alignment = Pos.TOP_LEFT
                VBox.setVgrow(this, Priority.ALWAYS)
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
            })
            // At the bottom of the note, there is a checkbox (“Archived”)
            // for changing the state of this note
            // (archived if box is checked or active if box is not checked).
            children.add(HBox().apply {
                children.add(checkBoxSquare1)
                children.add(archiveLabelSquare1)
                spacing = 10.0
            })
            // with height and width of 225 units,
            minWidth = 225.0
            minHeight = 225.0
            maxHeight = 225.0
            maxWidth = 225.0
            // and spacing, padding and corner radii of 10.
            padding = Insets(20.0)
            spacing = 10.0
            // Active notes have a light yellow background, archived notes a light gray one.
            backgroundProperty().bindBidirectional(rectangle4.backgroundProperty())
        }

        // In grid view, notes are displayed in the central area as square areas
        // Notes flow left-to-right and wrap if there is not enough horizontal space for an entire note.
        // Notes text that is too long to be displayed in a note is cut off.
        val gridView = TilePane(Orientation.HORIZONTAL).apply {
            children.add(square0)
            children.add(square1)
            children.add(square3)
            minWidthProperty().bind(stage.widthProperty())
            maxWidthProperty().bind(stage.widthProperty())
        }

        var rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
        var rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()

        var squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
        var squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()

        showArchivedCheckBox.selectedProperty().addListener { _, _, new ->
            if (new) { // showing archived notes in the currently active view
                listView.children.clear()
                listView.children.add(rectangle0)
                gridView.children.clear()
                gridView.children.add(square0)
                if (choiceBoxSort.selectionModel.isSelected(0)) { // asc
                    rectangleLengthAsc.forEach() { (rectangle, _) ->
                        if (rectangle in totalRectangleNotes) {
                            listView.children.add(rectangle)
                        }
                    }
                    squareLengthAsc.forEach() { (square, _) ->
                        if (square in totalSquareNotes) {
                            gridView.children.add(square)
                        }
                    }
                } else if (choiceBoxSort.selectionModel.isSelected(1)) { //desc
                    rectangleLengthDesc.forEach() { (rectangle, _) ->
                        if (rectangle in totalRectangleNotes) {
                            listView.children.add(rectangle)
                        }
                    }
                    squareLengthDesc.forEach() { (square, _) ->
                        if (square in totalSquareNotes) {
                            gridView.children.add(square)
                        }
                    }
                }
            } else { // hiding archived notes in the currently active view
                for (rectangle in totalRectangleNotes) {
                    if (rectangle !in activeRectangleNotes) {
                        listView.children.remove(rectangle)
                    }
                }
                for (square in totalSquareNotes) {
                    if (square !in activeSquareNotes) {
                        gridView.children.remove(square)
                    }
                }
            }
        }

        // Selecting a different “Order by” from the choice box
        // results in re-ordering the notes in the currently active view.
        choiceBoxSort.selectionModel.selectedItemProperty().addListener { _, _, new ->
            listView.children.clear()
            listView.children.add(rectangle0)
            gridView.children.clear()
            gridView.children.add(square0)
            if (new == "Length(asc)") {
                rectangleLengthAsc.forEach() { (rectangle, _) ->
                    if (showArchivedCheckBox.isSelected) {
                        if (rectangle in totalRectangleNotes) {
                            listView.children.add(rectangle)
                        }
                    } else {
                        if (rectangle in activeRectangleNotes) {
                            listView.children.add(rectangle)
                        }
                    }
                }
                squareLengthAsc.forEach() { (square, _) ->
                    if (showArchivedCheckBox.isSelected) {
                        if (square in totalSquareNotes) {
                            gridView.children.add(square)
                        }
                    } else {
                        if (square in activeSquareNotes) {
                            gridView.children.add(square)
                        }
                    }
                }
            } else if (new == "Length(desc)") {
                rectangleLengthDesc.forEach() { (rectangle, _) ->
                    if (showArchivedCheckBox.isSelected) {
                        if (rectangle in totalRectangleNotes) {
                            listView.children.add(rectangle)
                        }
                    } else {
                        if (rectangle in activeRectangleNotes) {
                            listView.children.add(rectangle)
                        }
                    }
                }
                squareLengthDesc.forEach() { (square, _) ->
                    if (showArchivedCheckBox.isSelected) {
                        if (square in totalSquareNotes) {
                            gridView.children.add(square)
                        }
                    } else {
                        if (square in activeSquareNotes) {
                            gridView.children.add(square)
                        }
                    }
                }
            }
        }

        checkBox1.selectedProperty().addListener{ _, _, new ->
            if (!new) { // show
                rectangle1.background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                square1.background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                ++activeNotesNumber
                activeRectangleNotes.add(rectangle1)
                activeSquareNotes.add(square1)
            } else { // hide
                rectangle1.background = Background(
                    BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                square1.background = Background(
                    BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                --activeNotesNumber
                activeRectangleNotes.remove(rectangle1)
                activeSquareNotes.remove(square1)
                if (!showArchivedCheckBox.isSelected) { // hide
                    listView.children.remove(rectangle1)
                    gridView.children.remove(square1)
                }
            }
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
        }
        checkBox2.selectedProperty().addListener{ _, _, new ->
            if (!new) {
                rectangle2.background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                ++activeNotesNumber
                activeRectangleNotes.add(rectangle2)
                activeSquareNotes.add(square2)
            } else {
                rectangle2.background = Background(
                    BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                --activeNotesNumber
                activeRectangleNotes.remove(rectangle2)
                activeSquareNotes.remove(square2)
                if (!showArchivedCheckBox.isSelected) { // hide
                    listView.children.remove(rectangle2)
                    gridView.children.remove(square2)
                }
            }
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
        }
        checkBox3.selectedProperty().addListener{ _, _, new ->
            if (!new) {
                rectangle3.background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                ++activeNotesNumber
                activeRectangleNotes.add(rectangle3)
                activeSquareNotes.add(square3)
            } else {
                rectangle3.background = Background(
                    BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                --activeNotesNumber
                activeRectangleNotes.remove(rectangle3)
                activeSquareNotes.remove(square3)
                if (!showArchivedCheckBox.isSelected) {
                    listView.children.remove(rectangle3)
                    gridView.children.remove(square3)
                }
            }
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
        }
        checkBox4.selectedProperty().addListener{ _, _, new ->
            if (!new) {
                rectangle4.background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                ++activeNotesNumber
                activeRectangleNotes.add(rectangle4)
                activeSquareNotes.add(square4)
            } else {
                rectangle4.background = Background(
                    BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                --activeNotesNumber
                activeRectangleNotes.remove(rectangle4)
                activeSquareNotes.remove(square4)
                if (!showArchivedCheckBox.isSelected) {
                    listView.children.remove(rectangle4)
                    gridView.children.remove(square4)
                }
            }
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
        }

        val textAreaRectangleEdit1 = TextArea()
        val saveButton1 = Button("Save").apply {
            minWidth = 75.0
            minHeight = 42.0
        }
        val discardButton1 = Button("Discard").apply {
            minWidth = 75.0
            minHeight = 42.0
        }

        rectangle1.onMouseClicked = EventHandler {
            rectangle1.children.remove(text1)
            rectangle1.children.remove(checkBoxRectangle1)
            rectangle1.children.remove(archiveLabelRectangle1)
            rectangle1.children.add(textAreaRectangleEdit1.apply{
                prefWidthProperty().bind(stage.widthProperty().subtract(220.0))
                maxHeight = 62.0
                text = text1.text
            })
            rectangle1.children.add(saveButton1)
            rectangle1.children.add(discardButton1)
        }

        saveButton1.onAction = EventHandler {
            text1.text = textAreaRectangleEdit1.text
            label1.text = textAreaRectangleEdit1.text
            textAreaRectangleEdit1.text = ""
            rectangle1.children.apply {
                remove(textAreaRectangleEdit1)
                remove(saveButton1)
                remove(discardButton1)
                add(text1)
                add(checkBoxRectangle1)
                add(archiveLabelRectangle1)
            }
            rectangleLengthMap[rectangle1] = text1.text.length
            squareLengthMap[square1] = label1.text.length
            rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
            squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
        }

        discardButton1.onAction = EventHandler {
            rectangle1.children.apply{
                remove(textAreaRectangleEdit1)
                remove(saveButton1)
                remove(discardButton1)
                add(text1)
                add(checkBoxRectangle1)
                add(archiveLabelRectangle1)
            }
        }

        val textAreaRectangleEdit2 = TextArea()
        val saveButton2 = Button("Save").apply {
            minWidth = 75.0
            minHeight = 42.0
        }
        val discardButton2 = Button("Discard").apply {
            minWidth = 75.0
            minHeight = 42.0
        }
        rectangle2.onMouseClicked = EventHandler {
            rectangle2.children.apply {
                remove(text2)
                remove(checkBoxRectangle2)
                remove(archiveLabelRectangle2)
                add(textAreaRectangleEdit2.apply{
                    prefWidthProperty().bind(stage.widthProperty().subtract(220.0))
                    maxHeight = 62.0
                    text = text1.text
                })
                add(saveButton2)
                add(discardButton2)
            }
        }

        saveButton2.onAction = EventHandler {
            text2.text = textAreaRectangleEdit2.text
            label2.text = textAreaRectangleEdit2.text
            textAreaRectangleEdit2.text = ""
            rectangle2.children.apply {
                remove(textAreaRectangleEdit2)
                remove(saveButton2)
                remove(discardButton2)
                add(text2)
                add(checkBoxRectangle2)
                add(archiveLabelRectangle2)
            }
            rectangleLengthMap[rectangle2] = text2.text.length
            squareLengthMap[square2] = label2.text.length
            rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
            squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
        }

        discardButton2.onAction = EventHandler {
            rectangle2.children.apply{
                remove(textAreaRectangleEdit2)
                remove(saveButton2)
                remove(discardButton2)
                add(text2)
                add(checkBoxRectangle2)
                add(archiveLabelRectangle2)
            }
        }

        val textAreaRectangleEdit3 = TextArea()
        val saveButton3 = Button("Save").apply {
            minWidth = 75.0
            minHeight = 42.0
        }
        val discardButton3 = Button("Discard").apply {
            minWidth = 75.0
            minHeight = 42.0
        }
        rectangle3.onMouseClicked = EventHandler {
            rectangle3.children.apply {
                remove(text3)
                remove(checkBoxRectangle3)
                remove(archiveLabelRectangle3)
                add(textAreaRectangleEdit3.apply{
                    prefWidthProperty().bind(stage.widthProperty().subtract(220.0))
                    maxHeight = 62.0
                    text = text1.text
                })
                add(saveButton3)
                add(discardButton3)
            }
        }

        saveButton3.onAction = EventHandler {
            text3.text = textAreaRectangleEdit3.text
            label3.text = textAreaRectangleEdit3.text
            textAreaRectangleEdit3.text = ""
            rectangle3.children.apply {
                remove(textAreaRectangleEdit3)
                remove(saveButton3)
                remove(discardButton3)
                add(text3)
                add(checkBoxRectangle3)
                add(archiveLabelRectangle3)
            }
            rectangleLengthMap[rectangle3] = text3.text.length
            squareLengthMap[square3] = label3.text.length
            rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
            squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
        }

        discardButton3.onAction = EventHandler {
            rectangle3.children.apply{
                remove(textAreaRectangleEdit3)
                remove(saveButton3)
                remove(discardButton3)
                add(text3)
                add(checkBoxRectangle3)
                add(archiveLabelRectangle3)
            }
        }

        val textAreaRectangleEdit4 = TextArea()
        val saveButton4 = Button("Save").apply {
            minWidth = 75.0
            minHeight = 42.0
        }
        val discardButton4 = Button("Discard").apply {
            minWidth = 75.0
            minHeight = 42.0
        }
        rectangle4.onMouseClicked = EventHandler {
            rectangle4.children.apply {
                remove(text4)
                remove(checkBoxRectangle4)
                remove(archiveLabelRectangle4)
                add(textAreaRectangleEdit1.apply{
                    prefWidthProperty().bind(stage.widthProperty().subtract(220.0))
                    maxHeight = 62.0
                    text = text1.text
                })
                add(saveButton4)
                add(discardButton4)
            }
        }

        saveButton4.onAction = EventHandler {
            text4.text = textAreaRectangleEdit4.text
            label4.text = textAreaRectangleEdit4.text
            textAreaRectangleEdit4.text = ""
            rectangle1.children.apply {
                remove(textAreaRectangleEdit4)
                remove(saveButton4)
                remove(discardButton4)
                add(text4)
                add(checkBoxRectangle4)
                add(archiveLabelRectangle4)
            }
            rectangleLengthMap[rectangle4] = text4.text.length
            squareLengthMap[square4] = label4.text.length
            rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
            squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
        }

        discardButton4.onAction = EventHandler {
            rectangle4.children.apply{
                remove(textAreaRectangleEdit4)
                remove(saveButton4)
                remove(discardButton4)
                add(text4)
                add(checkBoxRectangle4)
                add(archiveLabelRectangle4)
            }
        }


        // In both views, if there are too many notes to fit height-wise, a scrollbar appears to view them.
        var scrollPane = ScrollPane(listView).apply {
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        }

        // In the toolbar, the view selection button associated with the currently active grid is grayed out.
        // Clicking on the other button changes the view accordingly.
        // The setting regarding showing / hiding archived notes and note order must be maintained when switching between views.
        listButton.onAction = EventHandler {
            listButton.setDisable(!listButton.isDisable())
            gridButton.setDisable(!listButton.isDisable())
            scrollPane.content = listView
        }
        gridButton.onAction = EventHandler {
            gridButton.setDisable(!gridButton.isDisable())
            listButton.setDisable(!gridButton.isDisable())
            scrollPane.content = gridView
        }

        // Clicking on the “Clear” button removes all notes from the system
        clearButton.onAction = EventHandler {
            // updates the current view
            listView.children.clear()
            gridView.children.clear()
            // special note
            listView.children.add(rectangle0)
            gridView.children.add(square0)

            totalNotesNumber = 0
            activeNotesNumber = 0

            // retangle
            totalRectangleNotes.clear()
            activeRectangleNotes.clear()
            rectangleLengthMap.clear()

            // square
            totalSquareNotes.clear()
            activeSquareNotes.clear()
            squareLengthMap.clear()

            // update the status bar
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
        }

        // Clicking on it adds a new (active) note to the system.
        createRectangleButton.onAction = EventHandler {
            val checkBoxNew = CheckBox().apply { isSelected = false}
            val textNew = Text(textAreaRectangleCreate.text).apply {
                wrappingWidthProperty().bind(stage.widthProperty().subtract(130.0))
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
                HBox.setHgrow(this, Priority.ALWAYS)
            }
            val labelNew = Label(textAreaRectangleCreate.text).apply{
                isWrapText = true
                maxWidth = 200.0
                maxHeight = Double.MAX_VALUE
                alignment = Pos.TOP_LEFT
                VBox.setVgrow(this, Priority.ALWAYS)
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
            }

            val checkBoxRectangleNew = CheckBox().apply{
                selectedProperty().bindBidirectional(checkBoxNew.selectedProperty())
            }
            val archiveLabelRectangleNew = Label("Archived")

            val checkBoxSquareNew = CheckBox().apply{
                selectedProperty().bindBidirectional(checkBoxNew.selectedProperty())
            }
            val archiveLabelSquareNew = Label("Archived")

            val rectangleNew = HBox().apply {
                children.add(textNew)
                children.add(checkBoxRectangleNew)
                children.add(archiveLabelRectangleNew)
                padding = Insets(20.0)
                spacing = 10.0
                background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
            }
            val squareNew = VBox().apply {
                children.add(labelNew)
                children.add(HBox().apply {
                    children.add(checkBoxSquareNew)
                    children.add(archiveLabelSquareNew)
                    spacing = 10.0
                })
                minWidth = 225.0
                minHeight = 225.0
                maxHeight = 225.0
                maxWidth = 225.0
                padding = Insets(20.0)
                spacing = 10.0
                backgroundProperty().bindBidirectional(rectangleNew.backgroundProperty())
            }

            listView.children.add(rectangleNew)
            gridView.children.add(squareNew)

            ++activeNotesNumber
            activeRectangleNotes.add(rectangleNew)
            activeSquareNotes.add(squareNew)

            ++totalNotesNumber
            totalRectangleNotes.add(rectangleNew)
            totalSquareNotes.add(squareNew)

            rectangleLengthMap[rectangleNew] = textAreaRectangleCreate.text.length
            squareLengthMap[squareNew] = textAreaRectangleCreate.text.length

            rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()

            squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()

            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"

            checkBoxNew.selectedProperty().addListener{ _, _, new ->
                if (!new) { // not archived
                    rectangleNew.background = Background(
                        BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                    ++activeNotesNumber
                    activeRectangleNotes.add(rectangleNew)
                    activeSquareNotes.add(squareNew)
                } else { // archived
                    rectangleNew.background = Background(
                        BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                    --activeNotesNumber
                    activeRectangleNotes.remove(rectangleNew)
                    activeSquareNotes.remove(squareNew)
                    if (!showArchivedCheckBox.isSelected) {
                        listView.children.remove(rectangleNew)
                        gridView.children.remove(squareNew)
                    }
                }
                statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                        "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
            }
            textAreaRectangleCreate.text = ""

            // todo

            val textAreaRectangleEditNew = TextArea()
            val saveButtonNew = Button("Save").apply {
                minWidth = 75.0
                minHeight = 42.0
            }
            val discardButtonNew = Button("Discard").apply {
                minWidth = 75.0
                minHeight = 42.0
            }
            rectangleNew.onMouseClicked = EventHandler {
                rectangleNew.children.apply {
                    remove(textNew)
                    remove(checkBoxRectangleNew)
                    remove(archiveLabelRectangleNew)
                    add(textAreaRectangleEditNew.apply{
                        prefWidthProperty().bind(stage.widthProperty().subtract(220.0))
                        maxHeight = 62.0
                        text = text1.text
                    })
                    add(saveButtonNew)
                    add(discardButtonNew)
                }
            }

            saveButtonNew.onAction = EventHandler {
                textNew.text = textAreaRectangleEditNew.text
                labelNew.text = textAreaRectangleEditNew.text
                textAreaRectangleEditNew.text = ""
                rectangleNew.children.apply {
                    remove(textAreaRectangleEditNew)
                    remove(saveButtonNew)
                    remove(discardButtonNew)
                    add(textNew)
                    add(checkBoxRectangleNew)
                    add(archiveLabelRectangleNew)
                }
                rectangleLengthMap[rectangleNew] = textNew.text.length
                squareLengthMap[squareNew] = labelNew.text.length
                rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
                rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
                squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
                squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()
            }

            discardButtonNew.onAction = EventHandler {
                rectangleNew.children.apply{
                    remove(textAreaRectangleEditNew)
                    remove(saveButtonNew)
                    remove(discardButtonNew)
                    add(textNew)
                    add(checkBoxRectangleNew)
                    add(archiveLabelRectangleNew)
                }
            }
        }

        // Clicking on it adds a new (active) note to the system.
        createSquareButton.onAction = EventHandler {
            val newCheckBox = CheckBox().apply { isSelected = false}
            val newRectangle = HBox().apply {
                children.add(Text(textAreaSquareCreate.text).apply {
                    wrappingWidthProperty().bind(stage.widthProperty().subtract(130.0))
                    textAlignment = TextAlignment.LEFT
                    lineSpacing = 5.0
                    HBox.setHgrow(this, Priority.ALWAYS)
                })
                children.add(CheckBox().apply{
                    selectedProperty().bindBidirectional(newCheckBox.selectedProperty())
                })
                children.add(Label("Archived"))
                padding = Insets(20.0)
                spacing = 10.0
                background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
            }
            val newSquare = VBox().apply {
                children.add(Label(textAreaSquareCreate.text).apply{
                    isWrapText = true
                    maxWidth = 200.0
                    maxHeight = Double.MAX_VALUE
                    alignment = Pos.TOP_LEFT
                    VBox.setVgrow(this, Priority.ALWAYS)
                    textAlignment = TextAlignment.LEFT
                    lineSpacing = 5.0
                })
                children.add(HBox().apply {
                    children.add(CheckBox().apply{
                        selectedProperty().bindBidirectional(newCheckBox.selectedProperty())
                    })
                    children.add(Label("Archived"))
                    spacing = 10.0
                })
                minWidth = 225.0
                minHeight = 225.0
                maxHeight = 225.0
                maxWidth = 225.0
                padding = Insets(20.0)
                spacing = 10.0
                backgroundProperty().bindBidirectional(newRectangle.backgroundProperty())
            }

            listView.children.add(newRectangle)
            gridView.children.add(newSquare)

            ++activeNotesNumber
            activeRectangleNotes.add(newRectangle)
            activeSquareNotes.add(newSquare)

            ++totalNotesNumber
            totalRectangleNotes.add(newRectangle)
            totalSquareNotes.add(newSquare)

            rectangleLengthMap[newRectangle] = textAreaSquareCreate.text.length
            squareLengthMap[newSquare] = textAreaSquareCreate.text.length

            rectangleLengthAsc =  rectangleLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            rectangleLengthDesc = rectangleLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()

            squareLengthAsc =  squareLengthMap.toList().sortedBy{ (k,v) -> v}.toMap()
            squareLengthDesc = squareLengthMap.toList().sortedByDescending{ (k,v) -> v}.toMap()

            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
            newCheckBox.selectedProperty().addListener{ _, _, new ->
                if (!new) {
                    newRectangle.background = Background(
                        BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                    ++activeNotesNumber
                    activeRectangleNotes.add(newRectangle)
                    activeSquareNotes.add(newSquare)
                } else {
                    newRectangle.background = Background(
                        BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                    --activeNotesNumber
                    activeRectangleNotes.remove(newRectangle)
                    activeSquareNotes.remove(newSquare)
                    if (!showArchivedCheckBox.isSelected) {
                        listView.children.remove(newRectangle)
                        gridView.children.remove(newSquare)
                    }
                }
                statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"} " +
                        "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
            }
            textAreaSquareCreate.text = ""
        }

        // The toolbar and the status bar are visually separated from the main views.
        val root = BorderPane()
        // a toolbar at the top
        root.top = toolbar
        // the main content in the center
        root.center = scrollPane
        // a status bar at the bottom
        root.bottom = statusBar

        // setup and show the stage (window)
        stage.apply {
            // the application opens as a window named "Notes - j349hu"
            title = "Notes - j349hu"

            // the initial size of the application part of the window is 800 by 600 unites when opened
            scene = Scene(root, 800.0, 600.0)

            // the layout is "responsive" with a minimum size of 640 by 480 units
            minWidth = 640.0
            minHeight = 480.0
        }.show()
    }
}

