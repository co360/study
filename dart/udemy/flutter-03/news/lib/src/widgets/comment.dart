import 'package:flutter/material.dart';
import 'package:news/src/models/item_model.dart';

class Comment extends StatelessWidget {
  final int itemId;
  final Map<int, Future<ItemModel>> itemMap;

  Comment({this.itemId, this.itemMap});

  @override
  Widget build(BuildContext context) {}
}
