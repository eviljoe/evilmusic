var EM = {};

EM.EQ = (function() {
    var nodes;

    function EQ() {
        nodes = [];
    }

    EQ.prototype.create = function(context) {
        var prevNode = firstNode;
        var eqNodeCount = 0;

        for(var x = 0; x < eqNodeCount; x++) {
            var eqNode = createEQNode(context, freqs[x], 2.5);

            if(prevNode) {
                prevNode.connect(eqNode);
            }

            nodes.push(eqNode);
            prevNode = eqNode;
        }

        return prevNode;
    }

    EQ.prototype.connectAfter = function(lastNode) {
        var connected = false;

        if(nodes.length > 0) {
            lastNode.connect(nodes[0]);
            connected = true;
        }

        return connected;
    }

    EQ.prototype.getLastNode = function() {
        return nodes.length > 0 ? nodes[nodes.length - 1] : null;
    }

    EQ.prototype.createEQNode = function(context, freq, q) {
        var node = context.createBiquadFilter();

        node.type = node.PEAKING;
        node.frequency.value = freq;
        node.Q.value = q;

        return node;
    }

    return EQ();
});
