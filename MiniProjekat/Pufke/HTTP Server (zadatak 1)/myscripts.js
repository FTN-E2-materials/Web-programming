function changeIt(el)
{
	
	var table = document.getElementById('tabelaPacijenata');
    var cells = table.getElementsByTagName('td');

    for (var i = 0; i < cells.length; i++) {
        // Take each cell
        var cell = cells[i];
        // do something on onclick event for cell
        cell.onclick = function () {
            // Get the row id where the cell exists
            var rowId = this.parentNode.rowIndex;
            var rowSelected = table.getElementsByTagName('tr')[rowId];
       
            rowSelected.className = "ZARAZEN"
            rowSelected.cells[5].innerHTML = "ZARAZEN";

        }
    }
}