import javafx.application.Application
import javafx.collections.FXCollections.fill
import javafx.collections.FXCollections.observableArrayList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.*
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import jdk.jfr.Event
import java.util.SortedMap
import java.util.Stack

class Main : Application() {
    override fun start(stage: Stage) {

        var totalNotesNumber = 4
        var activeNotesNumber = 2
        val showArchivedCheckBox = CheckBox().apply { isSelected  = false }

        val listButton = Button("List")
        val gridButton = Button("Grid")

        val choiceBox = ChoiceBox(observableArrayList("Length(asc)","Length(desc)"))

        val groups = HBox(
            // viewsGroup
            Label("View:").apply { padding = Insets(5.0,0.0,0.0,0.0) },
            listButton.apply { prefWidth = 50.0 },
            gridButton.apply { prefWidth = 50.0 },
            Separator().apply { orientation = Orientation.VERTICAL },
            // archiveGroup
            Label("Show archived:").apply { padding = Insets(5.0,0.0,0.0,0.0) },
            showArchivedCheckBox.apply { padding = Insets(5.0,0.0,0.0,0.0) },
            // sortGroup
            Separator().apply { orientation = Orientation.VERTICAL },
            Label("Order by:").apply { padding = Insets(5.0,0.0,0.0,0.0) },
            choiceBox.apply { selectionModel.select(0) }
        )
        groups.spacing = 10.0

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
        val statusBar = Label("$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
                "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active").apply {
            padding = Insets(10.0)
        }

        val text1 = Text("This is a short note!")
        val text2 = Text("This note is slightly longer, but still manageable...")
        val text3 = Text("Active notes have a light yellow background, archived notes a light gray one.")
        val text4 = Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
                "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
                "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                "anim id est laborum.")

        val checkBox1 = CheckBox().apply{ isSelected = false }
        val checkBox2 = CheckBox().apply { isSelected = true }
        val checkBox3 = CheckBox().apply { isSelected = false }
        val checkBox4 = CheckBox().apply { isSelected = true }

        val rectangle1 = HBox()
        val rectangle2 = HBox()
        val rectangle3 = HBox()
        val rectangle4 = HBox()

        var totalRectangleNotes = mutableListOf(rectangle1, rectangle2, rectangle3, rectangle4)
        var activeRectangleNotes = mutableListOf(rectangle1, rectangle3)
        var rectangleLengthMap = mutableMapOf(
            text1.text.length to rectangle1,
            text2.text.length to rectangle2,
            text3.text.length to rectangle3,
            text4.text.length to rectangle4
        )

        val textArea = TextArea()
        val createRectangularButton = Button("Create")
        val rectangle0 = HBox().apply {
            children.add(textArea.apply {
                prefWidthProperty().bind(stage.widthProperty().subtract(120.0))
                maxHeight = 62.0
            })
            children.add(createRectangularButton.apply {
                minWidth = 75.0
                minHeight = 42.0
            })
            alignment = Pos.CENTER
            padding = Insets(20.0)
            spacing = 10.0
            background = Background(
                BackgroundFill(Color.LIGHTSALMON, CornerRadii(10.0), Insets(10.0))
            )
        }

        rectangle1.apply {
            children.add(text1.apply {
                wrappingWidthProperty().bind(stage.widthProperty().subtract(130.0))
                textAlignment = TextAlignment.LEFT
                lineSpacing = 5.0
                HBox.setHgrow(this, Priority.ALWAYS)
            })
            children.add(CheckBox().apply{
                selectedProperty().bindBidirectional(checkBox1.selectedProperty())
            })
            children.add(Label("Archived"))
            padding = Insets(20.0)
            spacing = 10.0
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
            children.add(CheckBox().apply{
                selectedProperty().bindBidirectional(checkBox2.selectedProperty())
            })
            children.add(Label("Archived"))
            padding = Insets(20.0)
            spacing = 10.0
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
            children.add(CheckBox().apply{
                selectedProperty().bindBidirectional(checkBox3.selectedProperty())
            })
            children.add(Label("Archived"))
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
            children.add(CheckBox().apply{
                selectedProperty().bindBidirectional(checkBox4.selectedProperty())
            })
            children.add(Label("Archived"))
            padding = Insets(20.0)
            spacing = 10.0
            background = Background(
                BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
        }

        val listView = VBox(rectangle0, rectangle1, rectangle3)

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
            label1.text.length to square1,
            label2.text.length to square2,
            label3.text.length to square3,
            label4.text.length to square4
        )

        val createSquareButton = Button("Created")
        val square0 = VBox().apply {
            children.add(TextArea().apply{
                textProperty().bindBidirectional(textArea.textProperty())
                isWrapText = true
                maxWidth = 200.0
                maxHeight = Double.MAX_VALUE
                alignment = Pos.TOP_LEFT
            })
            children.add(createSquareButton.apply {
                prefWidth = 205.0
            })
            minWidth = 225.0
            minHeight = 225.0
            maxHeight = 225.0
            maxWidth = 225.0
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
                children.add(CheckBox().apply{
                    selectedProperty().bindBidirectional(checkBox1.selectedProperty())
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
                children.add(CheckBox().apply{
                    selectedProperty().bindBidirectional(checkBox2.selectedProperty())
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
                children.add(CheckBox().apply{
                    selectedProperty().bindBidirectional(checkBox3.selectedProperty())
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
            children.add(HBox().apply {
                children.add(CheckBox().apply{
                    selectedProperty().bindBidirectional(checkBox4.selectedProperty())
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
            backgroundProperty().bindBidirectional(rectangle4.backgroundProperty())
        }

        val gridView = TilePane(Orientation.HORIZONTAL).apply {
            children.add(square0)
            children.add(square1)
            children.add(square3)
            minWidthProperty().bind(stage.widthProperty())
            maxWidthProperty().bind(stage.widthProperty())
        }

        var rectangleLengthAsc =  rectangleLengthMap.toSortedMap( { value, _,  -> value } )
        var rectangleLengthDesc = rectangleLengthMap.toSortedMap( { value, _,  -> -value } )
        var squareLengthAsc = squareLengthMap.toSortedMap( { value, _,  -> value } )
        var squareLengthDesc = squareLengthMap.toSortedMap( { value, _,  -> -value } )

        showArchivedCheckBox.selectedProperty().addListener { _, _, new ->
            if (new) {
                listView.children.clear()
                listView.children.add(rectangle0)
                gridView.children.clear()
                gridView.children.add(square0)
                if (choiceBox.selectionModel.isSelected(0)) {
                    rectangleLengthAsc.forEach() { _, rectangular ->
                        if (rectangular in totalRectangleNotes) {
                            listView.children.add(rectangular)
                        }
                    }
                    squareLengthAsc.forEach() { _, square ->
                        if (square in totalSquareNotes) {
                            gridView.children.add(square)
                        }
                    }
                } else if (choiceBox.selectionModel.isSelected(1)) {
                    rectangleLengthDesc.forEach() { _, rectangular ->
                        if (rectangular in totalRectangleNotes) {
                            listView.children.add(rectangular)
                        }
                    }
                    squareLengthDesc.forEach() { _, square ->
                        if (square in totalSquareNotes) {
                            gridView.children.add(square)
                        }
                    }
                }
            } else {
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

        choiceBox.selectionModel.selectedItemProperty().addListener { _, _, new ->
            listView.children.clear()
            listView.children.add(rectangle0)
            gridView.children.clear()
            gridView.children.add(square0)
            if (new == "Length(asc)") {
                rectangleLengthAsc.forEach() { _, rectangular ->
                    if (showArchivedCheckBox.isSelected) {
                        if (rectangular in totalRectangleNotes) {
                            listView.children.add(rectangular)
                        }
                    } else {
                        if (rectangular in activeRectangleNotes) {
                            listView.children.add(rectangular)
                        }
                    }
                }
                squareLengthAsc.forEach() { _, square ->
                    if (showArchivedCheckBox.isSelected) {
                        if (square in totalSquareNotes) {
                            gridView.children.add(square)
                        }
                    } else {
                        if (square in activeSquareNotes) {
                            listView.children.add(square)
                        }
                    }
                }
            } else if (new == "Length(desc)") {
                rectangleLengthDesc.forEach() { _, rectangular ->
                    if (showArchivedCheckBox.isSelected) {
                        if (rectangular in totalRectangleNotes) {
                            listView.children.add(rectangular)
                        }
                    } else {
                        if (rectangular in activeRectangleNotes) {
                            listView.children.add(rectangular)
                        }
                    }
                }
                squareLengthDesc.forEach() { _, square ->
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
            if (!new) {
                rectangle1.background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                square1.background = Background(
                    BackgroundFill(Color.LIGHTYELLOW, CornerRadii(10.0), Insets(10.0)))
                ++activeNotesNumber
                activeRectangleNotes.add(rectangle1)
                activeSquareNotes.add(square1)
            } else {
                rectangle1.background = Background(
                    BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                square1.background = Background(
                    BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), Insets(10.0)))
                --activeNotesNumber
                activeRectangleNotes.remove(rectangle1)
                activeSquareNotes.remove(square1)
                if (!showArchivedCheckBox.isSelected) {
                    listView.children.remove(rectangle1)
                    gridView.children.remove(square1)
                }
            }
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
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
                if (!showArchivedCheckBox.isSelected) {
                    listView.children.remove(rectangle2)
                    gridView.children.remove(square2)
                }
            }
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
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
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
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
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
        }

        var scrollPane = ScrollPane(listView).apply {
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        }

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

        clearButton.onAction = EventHandler {
            listView.children.clear()
            gridView.children.clear()
            totalNotesNumber = 0
            activeNotesNumber = 0
            // retangle
            totalRectangleNotes.clear()
            totalSquareNotes.clear()
            rectangleLengthMap.clear()
            rectangleLengthAsc.clear()
            rectangleLengthDesc.clear()
            // square
            totalSquareNotes.clear()
            activeSquareNotes.clear()
            squareLengthMap.clear()
            squareLengthAsc.clear()
            squareLengthDesc.clear()
            // update statusbar
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
                    "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
            // special note
            listView.children.add(rectangle0)
            gridView.children.add(square0)
        }

        createRectangularButton.onAction = EventHandler {
            val newCheckBox = CheckBox().apply { isSelected = false}
            val newRectangle = HBox().apply {
                children.add(Text(textArea.text).apply {
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
                children.add(Label(textArea.text).apply{
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
            rectangleLengthMap[textArea.text.length] = newRectangle
            squareLengthMap[textArea.text.length] = newSquare
            rectangleLengthAsc =  rectangleLengthMap.toSortedMap( { value, _,  -> value } )
            rectangleLengthDesc = rectangleLengthMap.toSortedMap( { value, _,  -> -value } )
            squareLengthAsc = squareLengthMap.toSortedMap( { value, _,  -> value } )
            squareLengthDesc = squareLengthMap.toSortedMap( { value, _,  -> -value } )
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
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
                statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
                        "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
            }
            textArea.text = ""
        }
        createSquareButton.onAction = EventHandler {
            val newCheckBox = CheckBox().apply { isSelected = false}
            val newRectangle = HBox().apply {
                children.add(Text(textArea.text).apply {
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
                children.add(Label(textArea.text).apply{
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
            rectangleLengthMap[textArea.text.length] = newRectangle
            squareLengthMap[textArea.text.length] = newSquare
            rectangleLengthAsc =  rectangleLengthMap.toSortedMap( { value, _,  -> value } )
            rectangleLengthDesc = rectangleLengthMap.toSortedMap( { value, _,  -> -value } )
            squareLengthAsc = squareLengthMap.toSortedMap( { value, _,  -> value } )
            squareLengthDesc = squareLengthMap.toSortedMap( { value, _,  -> -value } )
            statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
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
                statusBar.text = "$totalNotesNumber note${if (totalNotesNumber == 1) "," else "s,"}s, " +
                        "$activeNotesNumber of which ${if (activeNotesNumber == 1) "is" else "are"} active"
            }
            textArea.text = ""
        }

        val root = BorderPane()
        root.top = toolbar
        root.center = scrollPane
        root.bottom = statusBar

        // setup and show the stage (window)
        stage.apply {
            title = "CS349 - A1 Notes - j349hu"
            scene = Scene(root, 800.0, 600.0)
            minWidth = 640.0
            minHeight = 480.0
        }.show()
    }

}

