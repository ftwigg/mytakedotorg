import * as React from "react";
import * as ReactDOM from "react-dom";
import TakeEditor, { TitleNode, ParagraphNode, ConstitutionNode } from '../TakeEditor';
import Constitution from '../Constitution';
const { Block, Character, Html, Raw, Selection, Text } = require('slate');
const { List, Map } = require('immutable');
import * as key from "keycode";
import getNodeArray from "../../utils/getNodeArray";
const constitutionText = require('../../foundation/constitution.foundation.html');
import config from "./config";

const initialState: any = Raw.deserialize(config.initialState, { terse: true })

class MyTake extends React.Component<MyTakeProps, MyTakeState> {
  constructor(props: MyTakeProps){
    super(props);

    this.state = {
      constitutionNodes: this.getInitialText(),
      textIsHighlighted: false,
      highlightedNodes: [],
      editorState: initialState,
      schema: {
        nodes: {
          title: TitleNode,
          paragraph: ParagraphNode,
          constitution: ConstitutionNode,
          // p: (props: any) => <p {...props.attributes}>{props.children}</p>,
          // h2: (props: any) => <h2 {...props.attributes}>{props.children}</h2>,
          // h3: (props: any) => <h3 {...props.attributes}>{props.children}</h3>
        }
      }
    }

    this.handleConstitutionMouseUp = this.handleConstitutionMouseUp.bind(this);
    this.handleConstitutionClearClick = this.handleConstitutionClearClick.bind(this);
    this.handleConstitutionSetClick = this.handleConstitutionSetClick.bind(this);
    this.handleEditorChange = this.handleEditorChange.bind(this);
  }
  getInitialText(): Array<MyReactComponentObject> {
    const initialText = getNodeArray(constitutionText);
    return initialText;
  }
  clearDefaultDOMSelection(): void {
    if (window.getSelection) {
      if (window.getSelection().empty) {  // Chrome
        window.getSelection().empty();
      } else if (window.getSelection().removeAllRanges) {  // Firefox
        window.getSelection().removeAllRanges();
      }
    } else { 
      // pre IE 9, unsupported
    }
  }
  handleConstitutionClearClick(): void {
    this.setState({
      constitutionNodes: this.getInitialText(),  //Clear existing highlights
      textIsHighlighted: false
    });
  }
  handleConstitutionSetClick(): void {
    /**
       * TO-DO: Insert constitution after current selection.
       */
    let selection = Selection.create({
        anchorKey: "2",
        anchorOffset: 0,
        focusKey: "2",
        focusOffset: 0
      });
    
    console.log('highlighted nodes: ' + JSON.stringify(this.state.highlightedNodes));
    let newObject = {array: this.state.highlightedNodes};
    const properties = {
      data: Map(newObject),
      key: 'uniqueness',
      type: 'constitution'
    }

    const constitutionNode = Block.create(properties);
    const newState = this.state.editorState
      .transform()
      .insertBlockAtRange(selection, constitutionNode)
      .apply();

    /**
    * TO-DO: insert 'constitution' block with `document` fragment as child
    */
    // const { document } = serializer.deserialize("<p>html string</p><h2>heading2</h2><h3>heading3</h3>");
    // const newState = this.state.editorState
    //   .transform()
    //   .insertFragmentAtRange(selection, document)
    //   .apply();

    this.setState({editorState: newState});
  }
  handleConstitutionMouseUp(): void {
    if (window.getSelection && !this.state.textIsHighlighted) { // Pre IE9 will always be false
      let selection: Selection = window.getSelection();
      if (selection.toString().length) {  //Some text is selected
        let range: Range = selection.getRangeAt(0);
        this.highlightText(range);
      }
    }    
  }
  highlightText(range: Range): void {
    const indexOfStartContainer: number = Array.prototype.indexOf.call(
      range.startContainer.parentElement.parentNode.childNodes, //Arrange siblings into an array
      range.startContainer.parentNode);                         //Find indexOf current Node
    
    const indexOfSelectionStart: number = range.startOffset;

    const indexOfEndContainer: number = Array.prototype.indexOf.call(
      range.endContainer.parentElement.parentNode.childNodes, //Arrange siblings into an array
      range.endContainer.parentNode);                        //Find indexOf current Node

    const indexOfSelectionEnd: number = range.endOffset;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|~~~~~~~~~~~~~|
    //Need to find a way not to hardcode these indices ----------------v-------------v
    const startContainer: Node = ReactDOM.findDOMNode(this).childNodes[0].childNodes[2].childNodes[indexOfStartContainer];
    const endContainer: Node = ReactDOM.findDOMNode(this).childNodes[0].childNodes[2].childNodes[indexOfEndContainer];

    const { constitutionNodes } = this.state; 
    let newNodes: Array<MyReactComponentObject> = [...constitutionNodes.slice(0, indexOfStartContainer)];

    if (startContainer === endContainer) {
      // Create a new Span element with the contents of the highlighted text
      let newSpan: React.ReactNode = React.createElement(
        'span', 
        {
          className: 'constitution__text--selected', 
          key: 'startSpan',
          onClick: this.handleConstitutionSetClick
        }, 
        startContainer.textContent.substring(indexOfSelectionStart, indexOfSelectionEnd)
      );

      // Modify state array immutably
      let newNode: MyReactComponentObject = (Object as any).assign({}, this.state.constitutionNodes[indexOfStartContainer]);
      newNode.innerHTML = [
        startContainer.textContent.substring(0, indexOfSelectionStart),
        newSpan,
        startContainer.textContent.substring(indexOfSelectionEnd, startContainer.textContent.length)
      ];

      newNodes.push(newNode);

    } else {
      // Create a new Span element with the contents of the highlighted text
      let firstNewSpan: React.ReactNode = React.createElement(
        'span', 
        {
          className: 'constitution__text--selected', 
          key: 'startSpan',
          onClick: this.handleConstitutionSetClick
        }, 
        startContainer.textContent.substring(indexOfSelectionStart, startContainer.textContent.length)
      );
      
      // Modify state array immutably
      let firstNewNode: MyReactComponentObject = (Object as any).assign({}, this.state.constitutionNodes[indexOfStartContainer]);
      firstNewNode.innerHTML = [
        startContainer.textContent.substring(0, indexOfSelectionStart),
        firstNewSpan
      ];
      
      newNodes.push(firstNewNode);

      for(let index: number = indexOfStartContainer + 1; index < indexOfEndContainer ; index++){
        let nextNewNode: MyReactComponentObject = (Object as any).assign({}, this.state.constitutionNodes[index]);
        let key: string = 'middleSpan-' + index.toString();
        let nextNewSpan: React.ReactNode = React.createElement(
          'span',
          {
            className: 'constitution__text--selected', 
            key: key,
            onClick: this.handleConstitutionSetClick
          },
          nextNewNode.innerHTML
        )
        nextNewNode.innerHTML = [nextNewSpan];

        newNodes.push(nextNewNode);
      }    

      // Create a new Span element with the contents of the highlighted text
      let lastNewSpan: React.ReactNode = React.createElement(
        'span', 
        {
          className: 'constitution__text--selected', 
          key: 'endSpan',
          onClick: this.handleConstitutionSetClick
        }, 
        endContainer.textContent.substring(0, indexOfSelectionEnd)
      );
      // Modify state array immutably
      let lastNewNode: MyReactComponentObject = (Object as any).assign({}, this.state.constitutionNodes[indexOfEndContainer]);
      lastNewNode.innerHTML = [
        lastNewSpan,
        endContainer.textContent.substring(indexOfSelectionEnd, endContainer.textContent.length),
      ];

      newNodes.push(lastNewNode);
    }
    
    let newConstitutionFull: Array<MyReactComponentObject> = [
      ...newNodes,
      ...constitutionNodes.slice(indexOfEndContainer + 1, this.state.constitutionNodes.length)
    ]
    this.setState( prevState => ({
      constitutionNodes: [...newConstitutionFull],
      textIsHighlighted: true,
      highlightedNodes: [...newNodes]
    }));

    this.clearDefaultDOMSelection();
  }
  // On change, update the app's React state with the new editor state.
  handleEditorChange(editorState: TakeEditorOnChange): void {
    this.setState({ editorState })
  }
  handleEditorKeyDown(event: KeyboardEvent, data: any, state: any): any{
    // Determine whether cursor is in title block
    const isTitle = state.blocks.some((block: any) => block.type == 'title')
    
    let firstCharacterAfterTitle: TakeEditorSelection = Selection.create({
        anchorKey: "2",
        anchorOffset: 0,
        focusKey: "2",
        focusOffset: 0,
        isBackward: false,
        isFocused: true
      });

    // If enter is pressed in title block, move cursor to beginning of next block
    if (event.which == key('Enter') && isTitle) {
      
      const newState = state
        .transform()
        .select(firstCharacterAfterTitle)
        .apply();
      
      return newState;
    }
    if (event.which == key('Backspace')){
      /**
       * TO-DO: Don't allow first paragaph to be deleted from editor. Should remain empty, 
       * but not be removed from DOM.
       */

      // console.log(state.selection.hasStartAtStartOf(findDOMNode(state.block.nodes)) == firstCharacterAfterTitle);
      //console.log(state.startBlock.getPreviousSibling(state.startBlock.key));
    }

    return;
  }
  render(){
    return (
      <div>
        <Constitution 
          onClearClick={this.handleConstitutionClearClick}
          onSetClick={this.handleConstitutionSetClick}
          onMouseUp={this.handleConstitutionMouseUp} 
          constitutionNodes={this.state.constitutionNodes}
        />
        <TakeEditor 
          schema={this.state.schema}
          editorState={this.state.editorState}
          onChange={this.handleEditorChange}
          onKeyDown={this.handleEditorKeyDown}
        />
      </div>
    )
  }
}


export default MyTake;
