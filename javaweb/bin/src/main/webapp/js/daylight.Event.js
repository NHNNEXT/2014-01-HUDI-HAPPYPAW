/*
여기는 대부분 Jindo Framework의 jindo.$Event를 가져옴.. 필요한 것만 골라서 고칠 예정.
*/
daylight.Event = daylight.$Event = function(e) {
	var callee = arguments.callee;
	if (e instanceof callee) return e;
	if (!(this instanceof callee)) return new callee(e);
	
	if(e === undefined) e = window.event;
	var element = e.target || e.srcElement;


	if (element.nodeType == 3) //Text
		element = element.parentNode;
		
			
	var currentElement = e.currentTarget || element;
	
	this.target = this.element = element;
	this.currentElement = currentElement;
	this.type = e.type;
	this._event = e;
}
var _touch = function(e) {
	var te = {};
	if (e.touches) {
		te.changes = e.changedTouches;
		te.touches = e.touches;
		te.length = te.touches.length;
	}
	return te;
}, _pos = function(e, bGetOffset) {
	var body = document.body;
	var documentElement = document.documentElement;
	var left = body.scrollLeft || documentElement.scrollLeft;
	var top = body.scrollTop || documentElement.scrollTop;
	
	var pos = {
		clientX : e.clientX,
		clientY : e.clientY,
		pageX   : e.pageX,
		pageY   : e.pageY,
		layerX  : e.offsetX || e.layerX,
		layerY  : e.offsetY || e.layerY, //ie6 layerY
		screenX : e.screenX,
		screenY : e.screenY
	};
	return pos;
}, _touchOne = function(e) {

	if (e.touches) {
		var xy = (e.type === "touchend") ? _pos(e.changedTouches[0]) : _pos(e.touches[0]);
		return xy;
	}
	return {};
}

daylight.$Event.prototype.pos = function(bGetOffset) {
	return _pos(this._event, bGetOffset);
}
daylight.$Event.prototype.mouse = function(bGetOffset) {
	//return _pos(this._event, bGetOffset);
}
daylight.$Event.prototype.touch = function(e) {
	return _touch(this._event);
}
daylight.$Event.prototype.cross = function(e) {
	return daylight.$E.cross(this._event);
}

daylight.$Event.prototype.key = function() {
	var keycode = e.keyCode || e.charCode;
	return keycode;
}
daylight.$E = {
	pos : function(e) {return _pos(e); },
	touch : function(e) {return _touch(e);},
	cross : function(e) {
		var is_mobile = e.touches !== undefined;
		if(is_mobile) return _touchOne(e);
		else return _pos(e);
	}
}