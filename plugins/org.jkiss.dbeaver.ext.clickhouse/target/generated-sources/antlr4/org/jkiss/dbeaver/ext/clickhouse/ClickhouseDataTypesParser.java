// Generated from ClickhouseDataTypes.g4 by ANTLR 4.12.0

/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.clickhouse;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ClickhouseDataTypesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, Enum=8, Map=9, 
		Array=10, Tuple=11, Int=12, Float=13, Decimal=14, RightParen=15, LeftParen=16, 
		Space=17, Number=18, Identifier=19, Comma=20, Eq=21, String=22;
	public static final int
		RULE_type = 0, RULE_anyType = 1, RULE_markerType = 2, RULE_simpleType = 3, 
		RULE_enumType = 4, RULE_enumEntryList = 5, RULE_enumEntry = 6, RULE_tupleType = 7, 
		RULE_tupleElementList = 8, RULE_tupleElement = 9, RULE_arrayType = 10, 
		RULE_mapType = 11, RULE_stringType = 12, RULE_uuidType = 13, RULE_boolType = 14, 
		RULE_intType = 15, RULE_floatType = 16, RULE_ipv4Type = 17, RULE_ipv6Type = 18, 
		RULE_decimalType = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"type", "anyType", "markerType", "simpleType", "enumType", "enumEntryList", 
			"enumEntry", "tupleType", "tupleElementList", "tupleElement", "arrayType", 
			"mapType", "stringType", "uuidType", "boolType", "intType", "floatType", 
			"ipv4Type", "ipv6Type", "decimalType"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Nullable'", "'LowCardinality'", "'String'", "'UUID'", "'Boolean'", 
			"'IPV4'", "'IPV6'", null, "'Map'", "'Array'", "'Tuple'", null, null, 
			null, "')'", "'('", null, null, null, "','", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "Enum", "Map", "Array", 
			"Tuple", "Int", "Float", "Decimal", "RightParen", "LeftParen", "Space", 
			"Number", "Identifier", "Comma", "Eq", "String"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ClickhouseDataTypes.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ClickhouseDataTypesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public AnyTypeContext anyType() {
			return getRuleContext(AnyTypeContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ClickhouseDataTypesParser.EOF, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			anyType();
			setState(41);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnyTypeContext extends ParserRuleContext {
		public EnumTypeContext enumType() {
			return getRuleContext(EnumTypeContext.class,0);
		}
		public TupleTypeContext tupleType() {
			return getRuleContext(TupleTypeContext.class,0);
		}
		public MapTypeContext mapType() {
			return getRuleContext(MapTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public MarkerTypeContext markerType() {
			return getRuleContext(MarkerTypeContext.class,0);
		}
		public AnyTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyType; }
	}

	public final AnyTypeContext anyType() throws RecognitionException {
		AnyTypeContext _localctx = new AnyTypeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_anyType);
		try {
			setState(49);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Enum:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				enumType();
				}
				break;
			case Tuple:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				tupleType();
				}
				break;
			case Map:
				enterOuterAlt(_localctx, 3);
				{
				setState(45);
				mapType();
				}
				break;
			case Array:
				enterOuterAlt(_localctx, 4);
				{
				setState(46);
				arrayType();
				}
				break;
			case T__2:
			case T__3:
			case T__4:
			case T__5:
			case T__6:
			case Int:
			case Float:
			case Decimal:
				enterOuterAlt(_localctx, 5);
				{
				setState(47);
				simpleType();
				}
				break;
			case T__0:
			case T__1:
				enterOuterAlt(_localctx, 6);
				{
				setState(48);
				markerType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MarkerTypeContext extends ParserRuleContext {
		public TerminalNode LeftParen() { return getToken(ClickhouseDataTypesParser.LeftParen, 0); }
		public AnyTypeContext anyType() {
			return getRuleContext(AnyTypeContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(ClickhouseDataTypesParser.RightParen, 0); }
		public MarkerTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_markerType; }
	}

	public final MarkerTypeContext markerType() throws RecognitionException {
		MarkerTypeContext _localctx = new MarkerTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_markerType);
		try {
			setState(61);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(51);
				match(T__0);
				setState(52);
				match(LeftParen);
				setState(53);
				anyType();
				setState(54);
				match(RightParen);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				match(T__1);
				setState(57);
				match(LeftParen);
				setState(58);
				anyType();
				setState(59);
				match(RightParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleTypeContext extends ParserRuleContext {
		public IntTypeContext intType() {
			return getRuleContext(IntTypeContext.class,0);
		}
		public FloatTypeContext floatType() {
			return getRuleContext(FloatTypeContext.class,0);
		}
		public DecimalTypeContext decimalType() {
			return getRuleContext(DecimalTypeContext.class,0);
		}
		public StringTypeContext stringType() {
			return getRuleContext(StringTypeContext.class,0);
		}
		public UuidTypeContext uuidType() {
			return getRuleContext(UuidTypeContext.class,0);
		}
		public BoolTypeContext boolType() {
			return getRuleContext(BoolTypeContext.class,0);
		}
		public Ipv4TypeContext ipv4Type() {
			return getRuleContext(Ipv4TypeContext.class,0);
		}
		public Ipv6TypeContext ipv6Type() {
			return getRuleContext(Ipv6TypeContext.class,0);
		}
		public SimpleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleType; }
	}

	public final SimpleTypeContext simpleType() throws RecognitionException {
		SimpleTypeContext _localctx = new SimpleTypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_simpleType);
		try {
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Int:
				enterOuterAlt(_localctx, 1);
				{
				setState(63);
				intType();
				}
				break;
			case Float:
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				floatType();
				}
				break;
			case Decimal:
				enterOuterAlt(_localctx, 3);
				{
				setState(65);
				decimalType();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 4);
				{
				setState(66);
				stringType();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 5);
				{
				setState(67);
				uuidType();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 6);
				{
				setState(68);
				boolType();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 7);
				{
				setState(69);
				ipv4Type();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 8);
				{
				setState(70);
				ipv6Type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumTypeContext extends ParserRuleContext {
		public TerminalNode Enum() { return getToken(ClickhouseDataTypesParser.Enum, 0); }
		public TerminalNode LeftParen() { return getToken(ClickhouseDataTypesParser.LeftParen, 0); }
		public EnumEntryListContext enumEntryList() {
			return getRuleContext(EnumEntryListContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(ClickhouseDataTypesParser.RightParen, 0); }
		public EnumTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumType; }
	}

	public final EnumTypeContext enumType() throws RecognitionException {
		EnumTypeContext _localctx = new EnumTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_enumType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(Enum);
			setState(74);
			match(LeftParen);
			setState(75);
			enumEntryList();
			setState(76);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumEntryListContext extends ParserRuleContext {
		public List<EnumEntryContext> enumEntry() {
			return getRuleContexts(EnumEntryContext.class);
		}
		public EnumEntryContext enumEntry(int i) {
			return getRuleContext(EnumEntryContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(ClickhouseDataTypesParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(ClickhouseDataTypesParser.Comma, i);
		}
		public EnumEntryListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumEntryList; }
	}

	public final EnumEntryListContext enumEntryList() throws RecognitionException {
		EnumEntryListContext _localctx = new EnumEntryListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_enumEntryList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			enumEntry();
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(79);
				match(Comma);
				setState(80);
				enumEntry();
				}
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumEntryContext extends ParserRuleContext {
		public TerminalNode String() { return getToken(ClickhouseDataTypesParser.String, 0); }
		public TerminalNode Eq() { return getToken(ClickhouseDataTypesParser.Eq, 0); }
		public TerminalNode Number() { return getToken(ClickhouseDataTypesParser.Number, 0); }
		public EnumEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumEntry; }
	}

	public final EnumEntryContext enumEntry() throws RecognitionException {
		EnumEntryContext _localctx = new EnumEntryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_enumEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(String);
			setState(87);
			match(Eq);
			setState(88);
			match(Number);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TupleTypeContext extends ParserRuleContext {
		public TerminalNode Tuple() { return getToken(ClickhouseDataTypesParser.Tuple, 0); }
		public TerminalNode LeftParen() { return getToken(ClickhouseDataTypesParser.LeftParen, 0); }
		public TupleElementListContext tupleElementList() {
			return getRuleContext(TupleElementListContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(ClickhouseDataTypesParser.RightParen, 0); }
		public TupleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleType; }
	}

	public final TupleTypeContext tupleType() throws RecognitionException {
		TupleTypeContext _localctx = new TupleTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tupleType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(Tuple);
			setState(91);
			match(LeftParen);
			setState(92);
			tupleElementList();
			setState(93);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TupleElementListContext extends ParserRuleContext {
		public List<TupleElementContext> tupleElement() {
			return getRuleContexts(TupleElementContext.class);
		}
		public TupleElementContext tupleElement(int i) {
			return getRuleContext(TupleElementContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(ClickhouseDataTypesParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(ClickhouseDataTypesParser.Comma, i);
		}
		public TupleElementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleElementList; }
	}

	public final TupleElementListContext tupleElementList() throws RecognitionException {
		TupleElementListContext _localctx = new TupleElementListContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_tupleElementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			tupleElement();
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(96);
				match(Comma);
				setState(97);
				tupleElement();
				}
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TupleElementContext extends ParserRuleContext {
		public Token key;
		public AnyTypeContext value;
		public AnyTypeContext anyType() {
			return getRuleContext(AnyTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(ClickhouseDataTypesParser.Identifier, 0); }
		public TupleElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleElement; }
	}

	public final TupleElementContext tupleElement() throws RecognitionException {
		TupleElementContext _localctx = new TupleElementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tupleElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(103);
				((TupleElementContext)_localctx).key = match(Identifier);
				}
			}

			setState(106);
			((TupleElementContext)_localctx).value = anyType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayTypeContext extends ParserRuleContext {
		public TerminalNode Array() { return getToken(ClickhouseDataTypesParser.Array, 0); }
		public TerminalNode LeftParen() { return getToken(ClickhouseDataTypesParser.LeftParen, 0); }
		public AnyTypeContext anyType() {
			return getRuleContext(AnyTypeContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(ClickhouseDataTypesParser.RightParen, 0); }
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_arrayType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(Array);
			setState(109);
			match(LeftParen);
			setState(110);
			anyType();
			setState(111);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapTypeContext extends ParserRuleContext {
		public AnyTypeContext key;
		public AnyTypeContext value;
		public TerminalNode Map() { return getToken(ClickhouseDataTypesParser.Map, 0); }
		public TerminalNode LeftParen() { return getToken(ClickhouseDataTypesParser.LeftParen, 0); }
		public TerminalNode Comma() { return getToken(ClickhouseDataTypesParser.Comma, 0); }
		public TerminalNode RightParen() { return getToken(ClickhouseDataTypesParser.RightParen, 0); }
		public List<AnyTypeContext> anyType() {
			return getRuleContexts(AnyTypeContext.class);
		}
		public AnyTypeContext anyType(int i) {
			return getRuleContext(AnyTypeContext.class,i);
		}
		public MapTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapType; }
	}

	public final MapTypeContext mapType() throws RecognitionException {
		MapTypeContext _localctx = new MapTypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_mapType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(Map);
			setState(114);
			match(LeftParen);
			setState(115);
			((MapTypeContext)_localctx).key = anyType();
			setState(116);
			match(Comma);
			setState(117);
			((MapTypeContext)_localctx).value = anyType();
			setState(118);
			match(RightParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringTypeContext extends ParserRuleContext {
		public StringTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringType; }
	}

	public final StringTypeContext stringType() throws RecognitionException {
		StringTypeContext _localctx = new StringTypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_stringType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UuidTypeContext extends ParserRuleContext {
		public UuidTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_uuidType; }
	}

	public final UuidTypeContext uuidType() throws RecognitionException {
		UuidTypeContext _localctx = new UuidTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_uuidType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BoolTypeContext extends ParserRuleContext {
		public BoolTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolType; }
	}

	public final BoolTypeContext boolType() throws RecognitionException {
		BoolTypeContext _localctx = new BoolTypeContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_boolType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntTypeContext extends ParserRuleContext {
		public TerminalNode Int() { return getToken(ClickhouseDataTypesParser.Int, 0); }
		public IntTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intType; }
	}

	public final IntTypeContext intType() throws RecognitionException {
		IntTypeContext _localctx = new IntTypeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_intType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(Int);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FloatTypeContext extends ParserRuleContext {
		public TerminalNode Float() { return getToken(ClickhouseDataTypesParser.Float, 0); }
		public FloatTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatType; }
	}

	public final FloatTypeContext floatType() throws RecognitionException {
		FloatTypeContext _localctx = new FloatTypeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_floatType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(Float);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Ipv4TypeContext extends ParserRuleContext {
		public Ipv4TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ipv4Type; }
	}

	public final Ipv4TypeContext ipv4Type() throws RecognitionException {
		Ipv4TypeContext _localctx = new Ipv4TypeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ipv4Type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Ipv6TypeContext extends ParserRuleContext {
		public Ipv6TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ipv6Type; }
	}

	public final Ipv6TypeContext ipv6Type() throws RecognitionException {
		Ipv6TypeContext _localctx = new Ipv6TypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ipv6Type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DecimalTypeContext extends ParserRuleContext {
		public Token precision;
		public Token scale;
		public TerminalNode Decimal() { return getToken(ClickhouseDataTypesParser.Decimal, 0); }
		public TerminalNode LeftParen() { return getToken(ClickhouseDataTypesParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(ClickhouseDataTypesParser.RightParen, 0); }
		public List<TerminalNode> Number() { return getTokens(ClickhouseDataTypesParser.Number); }
		public TerminalNode Number(int i) {
			return getToken(ClickhouseDataTypesParser.Number, i);
		}
		public TerminalNode Comma() { return getToken(ClickhouseDataTypesParser.Comma, 0); }
		public DecimalTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decimalType; }
	}

	public final DecimalTypeContext decimalType() throws RecognitionException {
		DecimalTypeContext _localctx = new DecimalTypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_decimalType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(Decimal);
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LeftParen) {
				{
				setState(135);
				match(LeftParen);
				setState(138);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(136);
					((DecimalTypeContext)_localctx).precision = match(Number);
					setState(137);
					match(Comma);
					}
					break;
				}
				setState(140);
				((DecimalTypeContext)_localctx).scale = match(Number);
				setState(141);
				match(RightParen);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0016\u0091\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u00012\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003"+
		"\u0002>\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003H\b\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0005\u0005R\b\u0005\n\u0005\f\u0005U\t\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0005\bc\b\b\n"+
		"\b\f\bf\t\b\u0001\t\u0003\ti\b\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0003\u0013\u008b\b\u0013\u0001\u0013\u0001\u0013\u0003\u0013"+
		"\u008f\b\u0013\u0001\u0013\u0000\u0000\u0014\u0000\u0002\u0004\u0006\b"+
		"\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&\u0000"+
		"\u0000\u008e\u0000(\u0001\u0000\u0000\u0000\u00021\u0001\u0000\u0000\u0000"+
		"\u0004=\u0001\u0000\u0000\u0000\u0006G\u0001\u0000\u0000\u0000\bI\u0001"+
		"\u0000\u0000\u0000\nN\u0001\u0000\u0000\u0000\fV\u0001\u0000\u0000\u0000"+
		"\u000eZ\u0001\u0000\u0000\u0000\u0010_\u0001\u0000\u0000\u0000\u0012h"+
		"\u0001\u0000\u0000\u0000\u0014l\u0001\u0000\u0000\u0000\u0016q\u0001\u0000"+
		"\u0000\u0000\u0018x\u0001\u0000\u0000\u0000\u001az\u0001\u0000\u0000\u0000"+
		"\u001c|\u0001\u0000\u0000\u0000\u001e~\u0001\u0000\u0000\u0000 \u0080"+
		"\u0001\u0000\u0000\u0000\"\u0082\u0001\u0000\u0000\u0000$\u0084\u0001"+
		"\u0000\u0000\u0000&\u0086\u0001\u0000\u0000\u0000()\u0003\u0002\u0001"+
		"\u0000)*\u0005\u0000\u0000\u0001*\u0001\u0001\u0000\u0000\u0000+2\u0003"+
		"\b\u0004\u0000,2\u0003\u000e\u0007\u0000-2\u0003\u0016\u000b\u0000.2\u0003"+
		"\u0014\n\u0000/2\u0003\u0006\u0003\u000002\u0003\u0004\u0002\u00001+\u0001"+
		"\u0000\u0000\u00001,\u0001\u0000\u0000\u00001-\u0001\u0000\u0000\u0000"+
		"1.\u0001\u0000\u0000\u00001/\u0001\u0000\u0000\u000010\u0001\u0000\u0000"+
		"\u00002\u0003\u0001\u0000\u0000\u000034\u0005\u0001\u0000\u000045\u0005"+
		"\u0010\u0000\u000056\u0003\u0002\u0001\u000067\u0005\u000f\u0000\u0000"+
		"7>\u0001\u0000\u0000\u000089\u0005\u0002\u0000\u00009:\u0005\u0010\u0000"+
		"\u0000:;\u0003\u0002\u0001\u0000;<\u0005\u000f\u0000\u0000<>\u0001\u0000"+
		"\u0000\u0000=3\u0001\u0000\u0000\u0000=8\u0001\u0000\u0000\u0000>\u0005"+
		"\u0001\u0000\u0000\u0000?H\u0003\u001e\u000f\u0000@H\u0003 \u0010\u0000"+
		"AH\u0003&\u0013\u0000BH\u0003\u0018\f\u0000CH\u0003\u001a\r\u0000DH\u0003"+
		"\u001c\u000e\u0000EH\u0003\"\u0011\u0000FH\u0003$\u0012\u0000G?\u0001"+
		"\u0000\u0000\u0000G@\u0001\u0000\u0000\u0000GA\u0001\u0000\u0000\u0000"+
		"GB\u0001\u0000\u0000\u0000GC\u0001\u0000\u0000\u0000GD\u0001\u0000\u0000"+
		"\u0000GE\u0001\u0000\u0000\u0000GF\u0001\u0000\u0000\u0000H\u0007\u0001"+
		"\u0000\u0000\u0000IJ\u0005\b\u0000\u0000JK\u0005\u0010\u0000\u0000KL\u0003"+
		"\n\u0005\u0000LM\u0005\u000f\u0000\u0000M\t\u0001\u0000\u0000\u0000NS"+
		"\u0003\f\u0006\u0000OP\u0005\u0014\u0000\u0000PR\u0003\f\u0006\u0000Q"+
		"O\u0001\u0000\u0000\u0000RU\u0001\u0000\u0000\u0000SQ\u0001\u0000\u0000"+
		"\u0000ST\u0001\u0000\u0000\u0000T\u000b\u0001\u0000\u0000\u0000US\u0001"+
		"\u0000\u0000\u0000VW\u0005\u0016\u0000\u0000WX\u0005\u0015\u0000\u0000"+
		"XY\u0005\u0012\u0000\u0000Y\r\u0001\u0000\u0000\u0000Z[\u0005\u000b\u0000"+
		"\u0000[\\\u0005\u0010\u0000\u0000\\]\u0003\u0010\b\u0000]^\u0005\u000f"+
		"\u0000\u0000^\u000f\u0001\u0000\u0000\u0000_d\u0003\u0012\t\u0000`a\u0005"+
		"\u0014\u0000\u0000ac\u0003\u0012\t\u0000b`\u0001\u0000\u0000\u0000cf\u0001"+
		"\u0000\u0000\u0000db\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000"+
		"e\u0011\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000gi\u0005\u0013"+
		"\u0000\u0000hg\u0001\u0000\u0000\u0000hi\u0001\u0000\u0000\u0000ij\u0001"+
		"\u0000\u0000\u0000jk\u0003\u0002\u0001\u0000k\u0013\u0001\u0000\u0000"+
		"\u0000lm\u0005\n\u0000\u0000mn\u0005\u0010\u0000\u0000no\u0003\u0002\u0001"+
		"\u0000op\u0005\u000f\u0000\u0000p\u0015\u0001\u0000\u0000\u0000qr\u0005"+
		"\t\u0000\u0000rs\u0005\u0010\u0000\u0000st\u0003\u0002\u0001\u0000tu\u0005"+
		"\u0014\u0000\u0000uv\u0003\u0002\u0001\u0000vw\u0005\u000f\u0000\u0000"+
		"w\u0017\u0001\u0000\u0000\u0000xy\u0005\u0003\u0000\u0000y\u0019\u0001"+
		"\u0000\u0000\u0000z{\u0005\u0004\u0000\u0000{\u001b\u0001\u0000\u0000"+
		"\u0000|}\u0005\u0005\u0000\u0000}\u001d\u0001\u0000\u0000\u0000~\u007f"+
		"\u0005\f\u0000\u0000\u007f\u001f\u0001\u0000\u0000\u0000\u0080\u0081\u0005"+
		"\r\u0000\u0000\u0081!\u0001\u0000\u0000\u0000\u0082\u0083\u0005\u0006"+
		"\u0000\u0000\u0083#\u0001\u0000\u0000\u0000\u0084\u0085\u0005\u0007\u0000"+
		"\u0000\u0085%\u0001\u0000\u0000\u0000\u0086\u008e\u0005\u000e\u0000\u0000"+
		"\u0087\u008a\u0005\u0010\u0000\u0000\u0088\u0089\u0005\u0012\u0000\u0000"+
		"\u0089\u008b\u0005\u0014\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000"+
		"\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000"+
		"\u008c\u008d\u0005\u0012\u0000\u0000\u008d\u008f\u0005\u000f\u0000\u0000"+
		"\u008e\u0087\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000"+
		"\u008f\'\u0001\u0000\u0000\u0000\b1=GSdh\u008a\u008e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}