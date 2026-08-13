public class move {
    cell startCell;
    cell endCell;

    public move(cell startCell,cell endCell){
        this.startCell=startCell;
        this.endCell=endCell;
    }

    //Same weather user is oving their pieces
    public boolean isValid(){
        return !(startCell.getPiece().isWhite() == endCell.getPiece().isWhite());
    }

    public cell getStartCell(){
        return startCell;
    }

    public cell getendCell(){
        return endCell;
    }

}
