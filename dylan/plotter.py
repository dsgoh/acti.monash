import pandas as pd
import numpy as np
import plotly.plotly as py
import plotly.graph_objs as go


# Import data from csv
df = pd.read_csv('test.csv')
df.head()

trace1 = go.Scatter(x=df['x'], y=df['a'],mode='lines', name='a')


layout = go.Layout(title='Simple Plot from csv data',
                   plot_bgcolor='rgb(230, 230,230)')

fig = go.Figure(data=[trace1], layout=layout)

# Plot data in the notebook
py.iplot(fig, filename='simple-plot-from-csv')
