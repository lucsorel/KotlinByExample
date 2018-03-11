package components

import model.Configuration
import model.UiState
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.dom.button
import react.dom.div
import react.dom.h1
import react.dom.ul
import react.setState
import store.Store


/**
 * Create the main structure:
 *
 * ```html
 * <div class="main">
 *     <h1>Kotlin par l'exemple</h1>
 *     <button>Ajouter un verre</button>
 *     <button>Supprimer un verre</button>
 *     <div class="state initial"></div>
 *     <div class="state final"></div>
 *     <button class="btn-solve">Résoudre</button>
 *     <div class="error"></div>
 *     <ul></ul>
 * </div>
 * ```
 */

interface MainProps : RProps {
    var store: Store<UiState>
    var minCapacity: Int
    var maxCapacity: Int

    var onAddGlass: () -> Unit
    var onRemoveGlass: () -> Unit

    var onInitialCapacityChange: (Int, Int) -> Unit
    var onInitialCurrentChange: (Int, Int) -> Unit

    var onFinalCurrentChange: (Int, Int) -> Unit

    var onSolve: () -> Unit
}

interface MainState : RState {
    var uiState: UiState
}

class MainComponent(props: MainProps) : RComponent<MainProps, MainState>(props) {

    private var unsubscribe: Store.Unsubscribe? = null

    private val watcher = { newUiState: UiState ->
        setState {
            uiState = newUiState
        }
    }

    override fun MainState.init(props: MainProps) {
        uiState = props.store.state
    }

    override fun componentDidMount() {
        unsubscribe = props.store.subscribe({ it }, watcher)
    }

    override fun componentWillUnmount() {
        unsubscribe?.invoke()
    }

    override fun RBuilder.render() {
        div(classes = "main") {
            h1 { +"Kotlin par l'exemple" }
            button(classes = "btn-add") {
                +"Ajouter un verre"
                TODO("3.2")
            }
            button(classes = "btn-remove") {
                +"Supprimer un verre"
                TODO("3.1")
            }
            div(classes = "state initial") {
                initialState(state = state.uiState.initialState,
                             minCapacity = props.minCapacity,
                             maxCapacity = props.maxCapacity,
                             onInitialCapacityChange = props.onInitialCapacityChange,
                             onInitialCurrentChange = props.onInitialCurrentChange)
            }
            div(classes = "state final") {
                finalState(state = state.uiState.finalState,
                           onFinalCurrentChange = props.onFinalCurrentChange)
            }
            button(classes = "btn-solve") {
                +"Résoudre"
                TODO("4.1")
            }
            div(classes = "error") {
                +(state.uiState.error ?: "")
            }
            ul {
                if (state.uiState.solutionList.size > 1) {
                    solutionState(state.uiState.solutionList)
                }
            }
        }
    }
}

// Main container
fun RBuilder.mainContainer(config: Configuration, store: Store<UiState>): ReactElement =
    child(MainComponent::class) {
        // attributes
        attrs.store = store
        attrs.minCapacity = config.minCapacity
        attrs.maxCapacity = config.maxCapacity

        // actions
        attrs.onRemoveGlass = TODO("3.1")
        attrs.onAddGlass = TODO("3.2")

        attrs.onFinalCurrentChange = TODO("3.3")

        attrs.onInitialCurrentChange = TODO("3.4")
        attrs.onInitialCapacityChange = TODO("3.5")

        attrs.onSolve = TODO("4.1")
    }