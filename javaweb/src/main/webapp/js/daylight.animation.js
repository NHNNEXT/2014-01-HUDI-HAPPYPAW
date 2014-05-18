window.requestAnimFrame = (function(){
  return  window.requestAnimationFrame       ||
          window.webkitRequestAnimationFrame ||
          window.mozRequestAnimationFrame    ||
          function( callback ){
            window.setTimeout(callback, 1000 / 60);
          };
})();
//content width에 따라 바뀔 수 있는 속성
var lrtype = ["left", "right", "width", "margin-left", "margin-right", "padding-left", "padding-right"];
//content height에 따라 바뀔 수 있는 속성
var tbtype = ["top", "bottom", "height", "margin-top", "margin-bottom", "padding-top", "padding-bottom"];
//숫자로 치환할 수 있는 타입
var dtype = ["rotate", "opacity", "tx", "ty", "gtop", "gleft"];
var dimensionType = ["px", "em", "%"]

function _dot(a1,a2,b1,b2) {
	if(b1 + b2 == 0)
		return a1;
	return a1 * b1 / (b1 + b2) + a2 * b2 / (b1 + b2);
}
function _abspx(a, p100) {
	var v = parseFloat(a);
	if(p100 && a.indexOf("%") > -1)
		return v * p100 / 100;
	else
		return v;
}
function _getDimensionType(a) {
	var length = dimensionType.length;
	for(var i = 0; i < length; ++i) {
		if(a.indexOf(dimensionType[i]) != -1)
			return dimensionType[i];
		}
	return "";
}

daylight.animation = {
		//actionList : Dictionary(Object)
		CONSTANT : {
			browserPrefix : ["", "-webkit-", "-moz-", "-o-", "-ms-"],
			transformList : {"gleft":"translateX(?)","tx":"translateX(?)", "gtop":"translateY(?)","ty":"translateY(?)", "rotate":"rotate(?)", "scale" : "scale(?)", "rotateX":"rotateX(?)", "rotateY":"rotateY(?)"},
			browserEffectCSS : {"origin" : "transform-origin:?", "transition":"transition:?"},
			styleStartAnimation : "{prefix}animation: daylightAnimation{id} {time}s {type};\n{prefix}animation-fill-mode: forwards;\n{prefix}animation-iteration-count:{count};\n",
			stylePauseAnimation : "{prefix}animation-play-state:paused;\n",
			ignoreCSS : ["count", "time", "function", "length", "fill"]
		},
		prefixToBrowser : function(css, prefix) {
			prefix = prefix === undefined ? "all" : prefix;
			//prefix
			//all : prefix별로 바꿔준다.
			//-1 : prefix를 바꾸지 않고 그대로 둔다.
			//나머지 : 지정된 prefix로 바꿔준다.
			
			var CONSTANT = this.CONSTANT;
			var browserPrefixLength = CONSTANT.browserPrefix.length;
			var cssWithPrefix;
			
			switch(prefix) {
			case "all":
				var totalStyle = "";
				for(var i = 0; i < browserPrefixLength; ++i) {
					totalStyle +=daylight.replace("{prefix}", CONSTANT.browserPrefix[i], css);
				}
				return totalStyle;
				break;
			case -1:
				return css;
			default:
				cssWithPrefix = daylight.replace("{prefix}", prefix, css);
				if(prefix === "")
				 	return cssWithPrefix;
				 else
				 	return cssWithPrefix + daylight.replace("{prefix}", "", css);
			 }
		},
		/**
		*
		* @desc 애니메이션을 지원하는 브라우저인지 확인한다.
		* @return {boolean} 지원하는 브라우저이면 true, 아니면 false를 반환한다.
		*/
		checkBrowser : function() {
		
			return true;
		},
		/**
		*
		* @prarm {string} selector, CSS Selector
		* @prarm {object} property, value 쌍으로 이루어져 있는 Object
		* @prarm {string} prefix prefix가 없으면 모든 브라우저에 맞게 고쳐준다.
		* @desc CSS값들이 있는 Object를 CSSSelector가 포함된 style로 바꿔준다.
		*/
		objectToCSSWithSelector: function(selector, actionList, prefix) {
			var value = this.objectToCSS(actionList, prefix);
			var style = this.getCSSWithSelector(selector, value);
			
			return style;
		},
		/**
		/**
		*
		* @prarm {string} selector, CSS Selector
		* @prarm {string} CSS Value
		* @desc CSS값들이 있는 String을 CSSSelector가 포함된 style로 바꿔준다.
		*/
		getCSSWithSelector: function(selector, value) {
			var style = selector + "{";
			style += value;
			style += "}";
			
			return style;
		},
		/**
		*
		* @prarm {object} property, value 쌍으로 이루어져 있는 Object
		* @prarm {string} prefix prefix가 없으면 모든 브라우저에 맞게 고쳐준다.
		* @desc CSS값들이 있는 Object를 style로 바꿔준다.
		*/
		objectToCSS: function(actionList, prefix) {
			var CONSTANT = this.CONSTANT;
			var transformList = [];
			var browserEffectList = [];
			var otherList = [];
			var totalStyle = "";
			var transformStyle = "{prefix}transform:";
			var browserEffectStyle = "" ;
			var otherStyle = "";
			var action, replaceMotion;
			var j;
			prefix = prefix === undefined ? "all" : prefix;
			//prefix = -webkit-, -moz-, -ms-, -o-, "", "all", -1 : 고치지 않고 그대로.
			
			for(action in actionList) {
				if(action in CONSTANT.transformList)
					transformList.push(action);
				else if(action in CONSTANT.browserEffectCSS)
					browserEffectList.push(action);
				else if(CONSTANT.ignoreCSS.indexOf(action) != -1)
					continue;
				else 
					otherList.push(action);
			}
			if(transformList.length > 0) {
				for(j = 0; j < transformList.length; ++j) {
					action = transformList[j];
					replaceMotion = CONSTANT.transformList[action].replace("?", actionList[action]);
					transformStyle += " " + replaceMotion;
				}
				transformStyle += ";\n";
				totalStyle += this.prefixToBrowser(transformStyle, prefix);
			}
			if(browserEffectList.length > 0) {
				for(j = 0; j < browserEffectList.length; ++j) {
					action = browserEffectList[j];
					replaceMotion = "{prefix}" + CONSTANT.browserEffectCSS[action].replace("?", actionList[action]);
					browserEffectStyle += " " + replaceMotion;
					browserEffectStyle += ";\n";
				}
				totalStyle += this.prefixToBrowser(browserEffectStyle, prefix);

			}
			if(otherList.length > 0) {
				for(j = 0; j < otherList.length; ++j) {
					action = otherList[j];
					otherStyle += action+":"+actionList[action];
					otherStyle += ";\n";
				}
				totalStyle += otherStyle;
			}
			var cssStyle = daylight.replace("{prefix}", CONSTANT.browserPrefix[1], totalStyle);
			return cssStyle;
		},
		/**
		*
		* @class
		* @classdesc 타임라인
		*
		*/
		Timeline : function(selector) {
			console.log("NEW TIMELINE");
			
			this.selector = selector;
			var dl_object = this.dl_object = daylight(selector);
			this.layers = [];
			this.totalTime = 0;
			this.animationType = "ease";
			this.count = "infinite";
			
			dl_object.scroll(function(e) {e.preventDefault();});
			

			
		},//레이어를 만드는 함수
		/**
		*
		* @class
		* @classdesc 타임라인의 레이어
		*
		*/
		Layer : function(selector, initMotion) {
			this.selector = selector;
			var id = selector;
			id = daylight.replace(" ", "", id);
			id = daylight.replace(".", "", id);
			id = daylight.replace("#", "", id);
			
			this.id = id;
			this.dl_object = daylight(selector);
			
			if(this.dl_object.size() == 0) {
				throw new Error("레이어가 없습니다.");
			}
			
			
			this.motions = [];
			this.timeSchedule = {};
			this.initMotion = initMotion;
			this.totalTime = 0;
			this.properties = [];
			if(this.initMotion) {
				this.initMotion.time = -1;
				this.addMotion(this.initMotion);
			}
		}
};
daylight.animation.animationActions = {
	form : function(_layer, startTime, endTime, option) {
		
	},
	rotate : function(_layer, startTime, endTime, option) {
		var angle = parseFloat(option.angle) || 0;
		var prevRotate = parseFloat(_layer.getPrevMotion("rotate", startTime)["rotate"]) || 0;
		
		var motion = {startTime : startTime , endTime : endTime, start:{rotate : prevRotate + "deg"}, end:{rotate:(prevRotate + angle)+"deg"}};
		_layer.addMotion(motion);
		
	},
	fade : function(_layer, startTime, endTime, option, to) {
		var opacity = option && parseFloat(option.opacity) || to;		
		var prevOpacity = _layer.getPrevMotion("opacity", startTime)["opacity"];
		prevOpacity = typeof prevOpacity === "number" ? prevOpacity : 1;
		
		var motion = {startTime : startTime , endTime : endTime, start:{opacity:prevOpacity}, end:{opacity:opacity}};
		_layer.addMotion(motion);
	
	},
	/**
	* @desc fadein 액션
	*/	
	fadein : function(_layer, startTime, endTime, option) {
		this.fade(_layer, startTime, endTime, option, 1);	
	},
	/**
	* @desc fadeout 액션
	*/
	fadeout : function(_layer, startTime, endTime, option) {
		this.fade(_layer, startTime, endTime, option, 0);		
	},
	/**
	* @desc 이동 액션
	*/
	move : function(_layer, option) {
		if(!option)
			return;
		var moveMotion;
		var i, pos, left, top;
		var optionLength = option.length;
		//var dimension = _layer.dl_object.dimension();
		//top = daylight.type(top) === "string" ? parseFloat(top) : 0;
		//left = daylight.type(top) === "string" ? parseFloat(left) : 0;
		
		for(i = 0; 	i < optionLength; ++i) {
			moveMotion = option[i];
			if(moveMotion.hasOwnProperty("pos")) {
				pos = moveMotion["pos"];
				left = pos[1];
				top = pos[0];
				moveMotion.left = left;
				moveMotion.top = top;
				delete moveMotion.pos;
			}
			_layer.addMotion(moveMotion);
		}
	},
	/**
	* @desc flip 액션
	*/
	flip : function(_layer, startTime, endTime, option) {
		option = option || {};
		var startMotion = {time:startTime};
		var endMotion = {time:endTime};
		var rotate, toRotate;
		
		var property;
		var direction;
		//option.direction
		//left
		//right
		//top
		//bottom
		//rotateY
		//rotateX
		
		_layer.addMotion(startMotion);
		
		switch(option.direction) {

		case "left":
			property = "rotateY";
			direction = -1;
			break;
		case "top":
			property = "rotateX";
			direction = 1;
			break;
		case "bottom":
			property = "rotateX";
			direction = -1;
			break;
		case "right":
		default:
			property = "rotateY";
			direction = 1;
		}
		rotate = parseFloat(_layer.getPrevMotion(property, endTime)[property]) || 0;
		toRotate = (parseInt(rotate / 180) + direction) * 180;
		console.log(rotate);
		console.log(toRotate);
		endMotion[property] = toRotate + "deg";
		_layer.addMotion(endMotion);
	},
	loop: function(_layer, startTime, endTime, option) {
		option = option || {};
		if(!option.hasOwnProperty("list"))
			return;
		//count는 1번 이상.
		option.count = option.count || 1;
		
		//시간이 정해져있다면 그 시간안에 몇 번의 count가 될 수 있는지 쳌.
		if(option.hasOwnProperty("time"))
			option.count = parseInt((option.time - endTime) / (endTime - startTime));
			
		var count = option.count;
		var list = option.list;
		var loopMotion = option.list;
		var loopMotionLength = loopMotion.length;
		var loop = {};
		
		for(var i = 0; i < count; ++i)
			loop[i] = {};
			
		daylight.each(_layer.motions, function(o, index) { 
			if(o.time < startTime || o.time > endTime)
				return;
			
			var motion;
			var property;
			var time;
			for(var i = 0; i < count; ++i) {
				time = (endTime - startTime) * (i + 1) + o.time;
				motion = loop[i][o.time] = {time: time};
				for(property in o) {
					if(loopMotion.indexOf(property) == -1)
						continue;
					motion[property] = o[property];
				}
			}
		});
		
		for(i in loop) {
			for(var j in loop[i]) {
				_layer.addMotion(loop[i][j]);
			}
		}
	},
	/**
	* @desc 이미지 전환 액션
	*/
	sprite : function(_layer, startTime, endTime, option) {
		
	},
	disolve : function(_layer, startTime, endTime, option) {
		
	}
}
daylight.animation.Layer.prototype.fillMotion = function(motion, prevMotion, is_force) {
	var self = this;
	var ignoreCSS = daylight.animation.CONSTANT.ignoreCSS;
	//is_force -1이면 언제든지 바꾸기 요청이 오면 바꾸게 할 수 있다.
	if(!prevMotion)
		return;
	daylight.each(prevMotion, function(value, key){
		if(ignoreCSS.indexOf(key) >= 0)
			return;
			
		if(motion.hasOwnProperty(key + "?a") && !is_force) {
			motion[key] = value;
			delete motion[key + "?a"];
		} else if(!motion.hasOwnProperty(key) && !is_force)
			motion[key] = value;
		if(is_force == -1) {
			motion[key + "?a"] = value;
		}
			
	});	
}
daylight.animation.Layer.prototype.getPrevMotion = function(name, time) {
	var max_time = -2;
	var value = {};
	
	daylight.each(this.motions, function(o, index) {
		if(max_time > o.time || time < o.time)
			return;
			
		
		var tmp = o.hasOwnProperty(name + "?a") ? o[name +"?a"] : o[name];

		if(tmp !== undefined) {
			value = o;
			max_time = o.time;
		}
	});
	return value;
}
/*
	@param {string} css property
	@param {number} 찾고 싶은 시간
*/
daylight.animation.Layer.prototype.getNextMotion = function(name, time) {
	var min_time = 100000000;
	var value = {};
	
	daylight.each(this.motions, function(o, index) {
		if(min_time < o.time || time > o.time)
			return;

		var tmp = o.hasOwnProperty(name + "?a") ? o[name +"?a"] : o[name];
		if(tmp !== undefined) {
			value = o;
			min_time = o.time;
		}
	});
	return value;
}

/**
*	@param {object|object[]} motion / motion List / from Motion to Motion
*	@returns {Layer} this
*/
daylight.animation.Layer.prototype.addMotion = function(motion) {
	if(!motion)
		return;
	
	var type = daylight.type(motion);
	var ignoreCSS = daylight.animation.CONSTANT.ignoreCSS;
	var prevMotion = {time : -2};
	var self = this;
	var is_add = false;
	if(type === "array") {
		//다중 모션..ㅋㅋㅋㅋ
		var length = motion.length;
		for(var i = 0; i < length; ++i) {
			this.addMotion(motion[i]);
		}
		return this;
	}
	if(motion.hasOwnProperty("time")) {
		//한개만 추가...
		var time = motion.time;


		daylight.each(motion, function(v, k) {
			if(self.properties.indexOf(k) !== -1)
				return;
				
			if(ignoreCSS.indexOf(k) !== -1)
				return;
			
			self.properties.push(k);
		});
		
		
		daylight.each(this.motions, function(o, index) {
			if(o.time === time) {
				if(motion.fill === "add") {
					self.fillMotion(o, motion);
					is_add = true;
					return;
				}
				//겹치는게 있다면 살짝 차이를 줘서 바꿔
			}
			if(o.time >= startTime) {
				self.fillMotion(o, motion, -1);
				prevMotion = {};
			} else if(prevMotion.hasOwnProperty("time") && o.time <= time && o.time >= prevMotion.time) {
				daylight.each(o, function(value, key) {
					if(ignoreCSS.indexOf(key) == -1)
						prevMotion[key] = value;
				});
			}
		});
		
		if(time && this.totalTime < time) {
			this.totalTime = time;
		}	
		
		//이전 속성을 현재 속성에 추가. 존재하는 건 넣지 않는다.
		if(motion.fill === "auto" || motion.fill === "add" && !is_add) {
			delete motion.fill;
			this.fillMotion(motion, prevMotion);
		}
		if(!is_add)
			this.motions.push(motion);
		

		return this;
	}
	
	
	// from ---- to
	motion.from = motion.from || motion.start || {};
	motion.to = motion.to || motion.end || {};
	var startTime = motion.from && motion.from.time || motion.startTime;
					 
	var endTime = motion.to && motion.to.time || motion.endTime;


	motion.from.time = startTime;
	motion.to.time = endTime;
	
	this.addMotion(motion.from);
	this.addMotion(motion.to);
	
	return this;
};
daylight.animation.Layer.prototype.initTimer = function() {
	var motions = this.motions;
	var timeSchedule = this.timeSchedule;

	for(var i = 0; i < motions.length; ++i) {
		var motion = motions[i];
		var time = motion.time;
		if( time < 0)
			continue;
			
		for(var action in motion) {
			if(action.indexOf("?a") >= 0) {
				motion[action.replace("?a", "")] = motion[action];
				delete motion[action];
			}
		}
		if(time !== undefined)
			timeSchedule[time] = motion;
	}
	
	return this.getCSSInitMotion();
}
daylight.animation.Layer.prototype.getMotion = function(time) {
	time = parseFloat(time);
	var length = this.motions.length;
	for(var i = 0; i < length; ++i) {
		var motion = this.motions[i];
		if(motion.time === time)
			return motion;

	}
	return;
}
/**
*
* @param {number} time 애니메이션이 재생되고 있는 시간의 위치
* @param {string} property 찾고 싶은 CSS 속성
* @param {object} prevMotion time 이전의 property를 가지고 있는 모션.
* @param {object} nextvMotion time 이후의 property를 가지고 있는 모션.
* @return {string | number} 이전 시간과 이후 시간을 현재시간에 비례하여 내적을 한 값을 반환한다.
* @desc  이전 시간과 이후 시간을 현재시간에 비례하는 값을 찾아준다.
*/
daylight.animation.Layer.prototype.getTimeValue = function(time, property, prev, next) {
	var prevMotion = prev[property];
	var nextMotion = next[property];
	var dimension = "";
	
	var value = prevMotion;
	if(lrtype.indexOf(property) !== -1)
		dimension =  "width";
	else if(tbtype.indexOf(property) !== -1)
		dimension = "height";
	else if(dtype.indexOf(property) !== -1)
		dimension = "dimension";
	
	var prevTime = time - prev.time;
	var nextTime = next.time - time;
	if(dimension === "width" || dimension === "height") {
		var p100 = this.dl_object.dimension(dimension);//100퍼센트 기준으로 수치
		prevMotion = _abspx(prevMotion, p100);
		nextMotion = _abspx(nextMotion, p100);
		value = _dot(prevMotion, nextMotion, nextTime, prevTime) +"px";
	} else if(dimension === "dimension") {
		prevMotion = _abspx(prevMotion);
		nextMotion = _abspx(nextMotion);
		//console.log(prevMotion, nextMotion);
		value = _dot(prevMotion, nextMotion, nextTime, prevTime);
		
		switch(property) {
		case "rotate":
			value = value + "deg";
			break;
		case "tx":
		case "ty":
		case "gtop":
		case "gleft":
			value = value + "px";
			break;
		}
	} else {
		/*구분자*/
		//test
/*
		if(property === "border-radius") {
			var arr = [];
			var pr = prevMotion.split(" ");
			var ne = nextMotion.split(" ");
			var i, length, prevValue, nextValue, type;
			if(pr.length === ne.length) {
				length = pr.length;
				for(i = 0; i < length; ++i) {
					type = _getDimensionType(pr[i]);
					prevValue = parseFloat(pr[i]);
					nextValue = parseFloat(ne[i]);
					
					arr[i] = _dot(prevValue, nextValue, nextTime, prevTime) + type;
				}
			} else {
				var length = pr.lengh > ne.length ? pr.length : ne.length;
			}
			value = arr.join(" ");
		}
*/
		//margin
		//padding
		//border
		//border-radius
		
		return "transition";
	}
	return value;
}
daylight.animation.Layer.prototype.timer = function(time) {
	var properties = this.properties;
	var length = properties.length;
	var motions = {};
	var value, property, prev, next;
	var dl_object = this.dl_object;
	for(var i = 0; i < length; ++i) {
		property = properties[i];
		prev = this.getPrevMotion(property, time);
		next = this.getNextMotion(property, time);
		
		if(!prev.hasOwnProperty(property)) {
			if(daylight.animation.CONSTANT.transformList.hasOwnProperty(property))
				continue;

			
			if(property === "border-radius")
				console.log(time +"  "+ dl_object.css(property));

			prev[property] = dl_object.css(property);
			prev.time = 0;
			prev.count = 0;
			
			this.motions.push(prev);
			if(this.timeSchedule.hasOwnProperty(time))
				this.timeSchedule[time][property] = prev[property];
			else
				this.timeSchedule[time] = prev;
			
		}
		
		if(!next.hasOwnProperty(property))
			next = prev;
			
		value = this.getTimeValue(time, property, prev, next);
		if(value === "transition") {
			motions["transition"] =  property + " linear " + (next.time - prev.time) + "s";
			motions[property] = next[property];
		} else {
			motions[property] = value;
		}

		//_dot(time - prevMotion.time, nextMotion.time - time)	
	}

	var style = daylight.animation.objectToCSS(motions);
	dl_object.each(function(element) {
		//오리지날 스타일
		var ostyle = element.getAttribute("data-style") + ";" || "";

		element.setAttribute("style", ostyle + style);
	});
	return motions;
}
daylight.animation.Layer.prototype.getCSSInitMotion = function() {
	if(this.initMotion) {
		var styleHTML = daylight.animation.objectToCSSWithSelector(this.selector, this.initMotion);
		return styleHTML;
	}
	return "";
}
//count와. type 테스트 값.
daylight.animation.Layer.prototype.getCSSInit = function(count, type) {
	var timeSchedule = this.timeSchedule;
	var prefix = "-webkit-";
	var CONSTANT = daylight.animation.CONSTANT;
	var styleStartAnimation = CONSTANT.styleStartAnimation;
	var stylePauseAnimation = CONSTANT.stylePauseAnimation;
	var browserPrefix = CONSTANT.browserPrefix;
	var browserPrefixLength = browserPrefix.length;
	var id = this.id;
	var totalTime = this.totalTime;
	var selector = this.selector;
	var styleHTML = "";
	var percentage;
	for(var i = 0; i < browserPrefixLength; ++i) {
		prefix = browserPrefix[i];
		styleHTML += "@" + prefix +"keyframes daylightAnimation"+id+" {\n";
		for(var time in timeSchedule) {
			percentage = parseFloat(time) * 100 / totalTime;//시간을 %로 바꿔준다.
			
			styleHTML += daylight.animation.objectToCSSWithSelector(percentage +"%"
						,timeSchedule[time], prefix) +"\n";	
		}
		styleHTML += "}\n";
	}

	var data = {id: id, time: totalTime, count: count, type: type};
	styleStartAnimation = daylight.template(data, styleStartAnimation);
	
	
	styleHTML += daylight.animation.getCSSWithSelector(this.selector + ".animationStart"
				,daylight.animation.prefixToBrowser(styleStartAnimation)) +"\n";

	styleHTML += daylight.animation.getCSSWithSelector(this.selector + ".animationPause"
				,daylight.animation.prefixToBrowser(stylePauseAnimation)) +"\n";	

	
	return styleHTML;

}
daylight.animation.Timeline.prototype.hasLayer = function(layer) {
	var layers = this.layers;
	var t = daylight.type(_layer);
	var is_string = t === "string";
	var layerLength = layers.length;
	var _layer;

	for(var i = 0; i < layerLength; ++i) {
		_layer = layers[i];
		if(!is_string && _layer.dl_object.equal(layer.dl_object))
			return true;
		
		if(is_string && _layer.id != layer)
			continue;
		else if(!is_string && (layer != _layer))// && layers[i].layer.id != layer.id
			continue;
		
		return true;
	}
	return false;
}
daylight.animation.Timeline.prototype.getLayer = function(layer) {
	var layers = this.layers;
	var t = daylight.type(layer, true);
	var is_string = (t === "string");
	var _layer;
	if(t === "undefined")
		throw new Error("No Layer");
	
	for(var i = 0; i < layers.length; ++i) {
		_layer = layers[i];
		if(is_string && _layer.id != layer)
			continue;
		else if((t === "daylight" || t === "element") && !_layer.dl_object.equal(layer))
			continue;
		else if(layer.constructor === daylight.animation.Layer &&  _layer != layer)
			continue;

		
		return _layer;
	}
	
	return;
}
daylight.animation.Timeline.prototype.createLayer = function(selector, initMotion) {
	var a = new daylight.animation.Layer(selector, initMotion);
	var id = this.addLayer(a);
	if(id == null)
		return;
	
	return a;
}
daylight.animation.Timeline.prototype.addLayer = function(selector, initMotion) {

	var layer = (selector instanceof daylight.animation.Layer) ? selector : new daylight.animation.Layer(selector, initMotion);
	
	if(this.hasLayer(layer)) {
		console.log("이미 레이어가 있습니다. id : " + layer.id);
		return null;
	}

	layer.dl_object.addClass("daylightAnimationLayer");
	this.layers.push(layer);
	
	return layer.id;
}
daylight.animation.Timeline.prototype.setLayer = function(layer, initMotion) {
	if(!layer) {
		console.log("잘못된 레이어입니다.");
		return;
	}
	
	var _layer = this.getLayer(layer);
	
	if(!_layer) {
		console.log("레이어가 없습니다.");
		return;
	}
	_layer.initMotion = initMotion;
}
daylight.animation.Timeline.prototype.addAction = function(layer, name, startTime, endTime, option) {
	//fade-in
	//fade-out
	//move, zoom(not scale)
	//disolve(targe포함)
	
	var _layer = this.getLayer(layer);
	if(daylight.animation.animationActions.hasOwnProperty(name)) {
		daylight.animation.animationActions[name](_layer, startTime, endTime, option);
	} else {
		throw new Error("해당하는 엑션이 없습니다.")
	}
	return this;
}
daylight.animation.Timeline.prototype.addMotion = function(layer, motion) {
	if(!layer) {
		throw new Error("잘못된 레이어입니다.");
	}
	var _layer = this.getLayer(layer);
	if(!_layer) {
		throw new Error("레이어가 없습니다.");
	}
	_layer.addMotion(motion);
	return this;
}
daylight.animation.Timeline.prototype.fillTimeline = function(layer) {
	var motions = layer.motions;
	var totalTime = this.totalTime;
	var finalMotion = {time:0};
	var timeSchedule = {};
	for(var i = 0; i < motions.length; ++i) {
		var motion = motions[i];
		var time = motion.time;
		if( time < 0)
			continue;
		for(var action in motion) {
			if(action.indexOf("?a") >= 0) {
				motion[action.replace("?a", "")] = motion[action];
				delete motion[action];
			}
		}
		
		if(time !== undefined) {
			while(timeSchedule.hasOwnProperty(time)) {time += 0.00001;motion.time = time;}
			timeSchedule[time] = motion;
		}
		
		if(finalMotion.time <= time)
			finalMotion = motion;
	}
	if(!timeSchedule[totalTime])
		timeSchedule[totalTime] = finalMotion;
	
	layer.totalTime = totalTime;
	layer.timeSchedule = timeSchedule;
	
	return this;
}
daylight.animation.Timeline.prototype.getInitMotionCSS = function() {
	var styleHTML = '<style class="daylightAnimationInitStyle">\n';
	var layers = this.layers;
	var layerLength = layers.length;
	for(var i = 0; i < layerLength; ++i) {
		styleHTML += layers[i].getCSSInitMotion();
	}
	styleHTML += '</style>';
	
	return styleHTML;
}
daylight.animation.Timeline.prototype.initTimer = function() {
	console.log("INIT TIMELINE TIMER");
	this.is_timer = true;
	var layers = this.layers;
	var layerLength = layers.length;
	var dl_object, layer;
	for(var i = 0; i < layerLength; ++i) {
		if(this.totalTime < layers[i].totalTime)
			this.totalTime = layers[i].totalTime;
	}
	var totalTime = this.totalTime;
	
	for(var i = 0; i < layerLength; ++i) {
		layer = layers[i];
		dl_object = layer.dl_object;
		this.fillTimeline(layer);
		dl_object.each(function(element, index) {
			element.setAttribute("data-style", element.getAttribute("style"));
		});
	}
	
	var style = $(".daylightAnimationStyle, .daylightAnimationInitStyle");
	
	if(!style.isEmpty())//removeStyle
		style.remove();
		
	$("head").append(this.getInitMotionCSS());
	return this;
}
daylight.animation.Timeline.prototype.init = function() {
	console.log("INIT TIMELINE");

	var layers = this.layers;
	var layerLength = layers.length;

	var styleHTML = '<style class="daylightAnimationStyle">\n';

	for(var i = 0; i < layerLength; ++i) {
		if(this.totalTime < layers[i].totalTime)
			this.totalTime = layers[i].totalTime;
	}
	var totalTime = this.totalTime;
	
	for(var i = 0; i < layerLength; ++i) {
		var layer = layers[i];
		this.fillTimeline(layer);
		styleHTML += layer.getCSSInit(this.getCount(), this.getAnimationType());
	}
	
	styleHTML += '</style>';

	var style = $(".daylightAnimationStyle, .daylightAnimationInitStyle");
	
	if(!style.isEmpty())//removeStyle
		style.remove();
		
	$("head").append(styleHTML);
	$("head").append(this.getInitMotionCSS());

	return this;
}
daylight.animation.Timeline.prototype.executeFunction = function(schedule, spendTime, count) {
	
}
daylight.animation.Timeline.prototype.timer = function() {

	var self = this;
	var is_timer = this.is_timer;
	var time = Date.now();
	var spendTime = this.spendTime += (time - this.prevTime) / 1000;
	var nowTime = this.nowTime = spendTime % this.totalTime;
	var count = parseInt(spendTime / this.totalTime);
	var totalTime = this.totalTime;
	var layers = this.layers;
	
	this.prevTime = time;
	


	if(!this.is_start) {
		this.nowTime = 0;
		return;
	}
	if(this.is_pause)
		return;
		
	var is_finish = this.is_finish = false;
	
	daylight.each(layers, function(layer, index) {
		var schedule = layer.timeSchedule;
		daylight.each(schedule, function(motion, time) {
			if(!motion)
				return;

			//test
			

			if(count < motion.count)
				return;
			
			if(time > nowTime && (motion.count == count))
				return;
				
			motion.count++;
			daylight.each(motion, function(action, name) {
				if(typeof action === "function") {
					console.log("function");
					action(layer, parseFloat(time), nowTime);
					return;
				}
			
			});
			//infinite 무한대이면 계속 timer가 돌도록 설정
			//아니면 count가 돌아야할 횟수를 넘으면 종료
			is_finish = self.getCount() === "infinite" ? false :  count >= self.getCount();
		});
		
		if(is_timer) {
			layer.timer(nowTime);
		}
		
		//count 0일 떄
		//spendTime을 지난 것만 찾자
		
		
		//count 1일 때 
		//motion.count가 0인 것을 찾자.
	});
	
	if(is_finish) {
		this.is_finish = true;
		
		this.finish();
		
		console.log("FINISHED");
		return;
	}
	requestAnimFrame(this.timer.bind(this));
}
/**
*
* @desc 애니메이션을 종료한다.
*/
daylight.animation.Timeline.prototype.finish = function() {
	console.log("FINISH TIMELINE totalTime : " + this.totalTime);
	this.startTime = this.prevTime = this.nowTime = this.spendTime = 0;
	this.is_start = false;
	this.is_pause = false;
	this.is_finish = true;
	return this;
}
/**
*
* @desc 재생된 횟수를 초기화한다.
*/
daylight.animation.Timeline.prototype.initCount = function() {
	var layers = this.layers;
	daylight.each(layers, function(layer, index) {
		var schedule = layer.timeSchedule;
		daylight.each(schedule, function(motion, time) {
			motion.count = 0;
		});
	});
}
/**
*
* @desc 애니메이션을 시작한다.
*/
daylight.animation.Timeline.prototype.start = function() {
	console.log("START TIMELINE totalTime : " + this.totalTime);
	$(".daylightAnimationLayer").addClass("animationStart");
	$(".daylightAnimationLayer").removeClass("animationPause");
	this.initCount();
	
	this.startTime = this.prevTime = Date.now();
	this.nowTime = this.spendTime = 0;
	requestAnimFrame(this.timer.bind(this));
	this.is_start = true;
	this.is_finish = false;
	this.is_pause = false;
	return this;
}
/**
*
* @desc 일시중지
*/
daylight.animation.Timeline.prototype.pause = function() {
	console.log("PAUSE TIMELINE");
	$(".daylightAnimationLayer").toggleClass("animationPause");
	this.is_pause = !this.is_pause;
	if(!this.is_pause) {
		this.prevTime = Date.now();
		requestAnimFrame(this.timer.bind(this));
	}
	return this;
}
daylight.animation.Timeline.prototype.showAnimationBar = function() {
	
}

daylight.defineGetterSetter(daylight.animation.Timeline, "animationType");
daylight.defineGetterSetter(daylight.animation.Timeline, "count");
daylight.defineGetter(daylight.animation.Timeline, "is_finish");
daylight.defineGetter(daylight.animation.Timeline, "is_start");
daylight.defineGetter(daylight.animation.Timeline, "is_pause");
daylight.extend(daylight.animation);