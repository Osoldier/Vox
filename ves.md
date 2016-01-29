# .VES File (Voxel Engine Scene)

## Version 0

|Field | size | template |
|:------|:-----|:--------|
|VES version | 1 byte | 0 |
|Player position | 12 bytes | XXXXYYYYZZZZ |
|World size | 8 bytes | XXXXZZZZ |
|Seed | 8 bytes | SEED |
|Modified Voxel | B(12+2 bytes)| XXXXYYYYZZZZ VoxelType|
