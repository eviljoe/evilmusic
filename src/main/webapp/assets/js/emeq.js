var EM = {};

EM.EQ = function() {
    this.nodes = [];
};

EM.EQ.prototype.create = function(context) {
    var prevNode = null;
    var freqs = [55, 77, 110, 156, 311, 440, 622, 880, 1200, 1800, 3500, 5000, 7000, 10000, 14000, 20000];

    for(var x = 0; x < freqs.length; x++) {
        var eqNode = this.createEQNode(context, freqs[x], 2.5);

        if(prevNode) {
            prevNode.connect(eqNode);
        }

        this.nodes.push(eqNode);
        prevNode = eqNode;
    }

    return prevNode;
};

EM.EQ.prototype.connectAfter = function(lastNode) {
    var connected = false;

    if(this.nodes.length > 0) {
        lastNode.connect(this.nodes[0]);
        connected = true;
    }

    return connected;
};

EM.EQ.prototype.getLastNode = function() {
    return this.nodes && this.nodes.length > 0 ? this.nodes[this.nodes.length - 1] : null;
};

EM.EQ.prototype.createEQNode = function(context, freq, q) {
    var node = context.createBiquadFilter();

    node.type = 'peaking';
    node.frequency.value = freq;
    node.Q.value = q;

    return node;
};

EM.EQNode = function() {
    this.frequency = null;
    this.q = null;
    this.gain = 0;
    this.nodes = [];
};