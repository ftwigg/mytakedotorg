import * as React from "react";
import * as ReactDOM from "react-dom";
import { FoundationTextType, FoundationNode } from "../Foundation";
import {
  getStartRangeOffsetTop,
  getNodeArray,
  getHighlightedNodes,
  highlightText,
  HighlightedText
} from "../../utils/functions";

interface AmendmentsProps {
  backButtonText?: string;
  onBackClick: () => void;
  onSetClick: (type: FoundationTextType, range: [number, number]) => void;
  range?: [number, number];
  offset?: number;
}

interface AmendmentsState {
  amendmentsNodes: FoundationNode[];
  range: [number, number];
  textIsHighlighted: boolean;
  alwaysHighlightedNodes: FoundationNode[];
  style: any;
}

class Amendments extends React.Component<AmendmentsProps, AmendmentsState> {
  constructor(props: AmendmentsProps) {
    super(props);

    this.state = {
      amendmentsNodes: getNodeArray("AMENDMENTS"),
      range: props.range,
      textIsHighlighted: false,
      alwaysHighlightedNodes: [],
      style: {}
    };
  }
  handleClearClick = () => {
    this.setState({
      amendmentsNodes: getNodeArray("AMENDMENTS"), //Clear existing highlights
      textIsHighlighted: false
    });
  };
  handleMouseUp = () => {
    if (window.getSelection && !this.state.textIsHighlighted) {
      // Pre IE9 will always be false
      let selection: Selection = window.getSelection();
      if (selection.toString().length) {
        //Some text is selected
        let range: Range = selection.getRangeAt(0);

        const highlightedText: HighlightedText = highlightText(
          range, // HTML Range, not [number, number] as in props.range
          [...this.state.amendmentsNodes],
          "AMENDMENTS",
          ReactDOM.findDOMNode(this).childNodes,
          this.handleSetClick
        );

        this.setState({
          amendmentsNodes: highlightedText.newNodes,
          range: highlightedText.range,
          textIsHighlighted: true
        });
      }
    }
  };
  handleSetClick = () => {
    this.props.onSetClick("AMENDMENTS", this.state.range);
  };
  componentDidMount() {
    this.setup();
  }
  componentWillUnmount() {
    this.tearDown();
  }
  setup = () => {
    if (this.props.range) {
      // Show Amendments over an existing take with pre-existing and uneditable highlights
      document.body.classList.add("noscroll");

      let alwaysHighlightedNodes = getHighlightedNodes(
        [...this.state.amendmentsNodes],
        this.props.range
      );

      let theseDOMNodes = ReactDOM.findDOMNode(this).childNodes;

      let offsetTop = getStartRangeOffsetTop(
        "AMENDMENTS",
        theseDOMNodes,
        this.props.range
      );

      this.setState({
        alwaysHighlightedNodes: alwaysHighlightedNodes,
        style: { top: offsetTop - 20 }
      });
    } else {
      document.body.classList.remove("noscroll");
    }
  };
  tearDown = () => {
    document.body.classList.remove("noscroll");
  };
  render() {
    let classes = "amendments";
    if (this.props.range) {
      classes += " amendments--overlay";
    } else {
      classes += " amendments--static";
    }
    return (
      <div className={classes}>
        <div className="amendments__header">
          <button onClick={this.props.onBackClick}>
            {this.props.backButtonText
              ? this.props.backButtonText
              : "Back to Foundation"}
          </button>
          <button onClick={this.handleClearClick}>Clear Selection</button>
          <h2 className="amendments__heading">
            Amendments to the Constitution
          </h2>
        </div>
        <div className="amendments__row">
          <div className="amendments__row-inner">
            <div className="amendments__text" onMouseUp={this.handleMouseUp}>
              {this.state.amendmentsNodes.map(function(
                element: FoundationNode,
                index: number
              ) {
                element.props["key"] = index.toString();
                return React.createElement(
                  element.component,
                  element.props,
                  element.innerHTML
                );
              })}
            </div>
            {this.props.range
              ? <div
                  className="editor__block editor__block--overlay"
                  style={this.state.style}
                >
                  <div className="editor__document editor__document--overlay">
                    {this.state.alwaysHighlightedNodes.map((node, index) => {
                      node.props["key"] = index.toString();
                      return React.createElement(
                        node.component,
                        node.props,
                        node.innerHTML
                      );
                    })}
                  </div>
                </div>
              : null}
          </div>
        </div>
      </div>
    );
  }
}

export default Amendments;
